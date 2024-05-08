package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.AnnouncementRequestDTO;
import com.jefy.ibp.dtos.ResponseDTO;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import com.jefy.ibp.services.AnnouncementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.jefy.ibp.dtos.Constants.ANNOUNCEMENTS_URL;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.*;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 06/05/2024
 */
@RestController
@RequestMapping(ANNOUNCEMENTS_URL)
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @GetMapping
    @Operation(
            summary = "Get all the announcements",
            description = "Fetch a page of announcements",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    public ResponseEntity<ResponseDTO> getAnnouncements(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ResponseDTO.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(OK)
                        .statusCode(OK.value())
                        .message("announcements fetched successfully")
                        .data(of("announcements", announcementService.getAll(page,size)))
                        .build()
        );
    }

    @GetMapping("/{announcement_id}")
    @Operation(
            summary = "Get announcement by Id",
            description = "fetch a announcement using the id",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404"),
            }
    )
    public ResponseEntity<ResponseDTO> get(@PathVariable("announcement_id") Long announcementId) {
        try {
            return ResponseEntity.ok(ResponseDTO.builder()
                            .timeStamp(LocalDateTime.now())
                            .status(OK)
                            .statusCode(OK.value())
                            .message("Announcement fetched successfully")
                            .data(of("announcement", announcementService.getById(announcementId)))
                            .build()

            );
        } catch (RecordNotFoundException e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                            .timeStamp(LocalDateTime.now())
                            .status(NOT_FOUND)
                            .statusCode(NOT_FOUND.value())
                            .message("Announcement not found")
                            .errors(of("errors", e.getMessage()))
                            .build()
            );
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Make an announcement  [For admin only]",
            description = "register an announcement",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request/ Invalid parameter", responseCode = "400"),
            }
    )
    public ResponseEntity<ResponseDTO> register(@RequestBody AnnouncementRequestDTO announcementRequestDTO) {
        try {
            return ResponseEntity.ok(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(CREATED)
                    .statusCode(CREATED.value())
                    .message("Announcement added successfully")
                    .data(of("announcement", announcementService.create(announcementRequestDTO)))
                    .build()

            );
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .message("Could not create an new announcement")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update an announcement  [For admin only]",
            description = "modifies an existing announcement",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request/ Invalid parameter", responseCode = "400"),
            }
    )
    public ResponseEntity<ResponseDTO> update(@RequestBody AnnouncementRequestDTO announcementRequestDTO) {
        try {
            return ResponseEntity.ok(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("Announcement updated successfully")
                    .data(of("announcement", announcementService.update(announcementRequestDTO)))
                    .build()

            );
        } catch (IllegalArgumentException | RecordNotFoundException e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .message("Could not update announcement")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
    }

    @DeleteMapping("/{announcement_id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete an announcement  [For admin only]",
            description = "Delete an existing announcement",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request/ Invalid parameter", responseCode = "400"),
            }
    )
    public ResponseEntity<ResponseDTO> delete(@PathVariable("announcement_id") Long announcementId) {

        try {
            announcementService.delete(announcementId);
            return ResponseEntity.ok(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("Announcement deleted successfully")
                    .build()

            );
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .message("Announcement does not exist")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
    }
}
