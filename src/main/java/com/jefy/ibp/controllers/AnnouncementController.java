package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.AnnouncementRequest;
import com.jefy.ibp.dtos.AnnouncementResponse;
import com.jefy.ibp.dtos.ConfirmationResponse;
import com.jefy.ibp.services.AnnouncementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.jefy.ibp.dtos.Constants.ANNOUNCEMENTS_URL;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 06/05/2024
 */
@RestController
@RequestMapping(ANNOUNCEMENTS_URL)
@RequiredArgsConstructor
@Tag(name = "Announcement")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get all the announcements",
            description = "Fetch a page of announcements",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    public ResponseEntity<Page<AnnouncementResponse>> getAnnouncements(@RequestParam(
            value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.status(OK).body(announcementService.getAll(page,size));
    }

    @GetMapping(path = "/{announcement_id}", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get announcement by Id",
            description = "fetch a announcement using the id",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404"),
            }
    )
    public ResponseEntity<AnnouncementResponse> get(@PathVariable("announcement_id") Long announcementId) {
            return ResponseEntity.status(OK).body(announcementService.getById(announcementId));
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Make an announcement  [For admin only]",
            description = "register an announcement",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request/ Invalid parameter", responseCode = "400"),
            }
    )
    public ResponseEntity<AnnouncementResponse> register(@RequestBody @Valid AnnouncementRequest announcementRequest) {
        return ResponseEntity.status(OK).body(announcementService.create(announcementRequest));
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update an announcement  [For admin only]",
            description = "modifies an existing announcement",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request/ Invalid parameter", responseCode = "400"),
            }
    )
    public ResponseEntity<AnnouncementResponse> update(@RequestBody @Valid AnnouncementRequest announcementRequest) {
        return ResponseEntity.status(OK).body(announcementService.update(announcementRequest));
    }

    @DeleteMapping(path = "/{announcement_id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete an announcement  [For admin only]",
            description = "Delete an existing announcement",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request/ Invalid parameter", responseCode = "400"),
            }
    )
    public ResponseEntity<ConfirmationResponse> delete(@PathVariable("announcement_id") Long announcementId) {
        announcementService.delete(announcementId);
        return ResponseEntity.status(OK).body(new ConfirmationResponse("Announcement deleted successfully"));
    }
}
