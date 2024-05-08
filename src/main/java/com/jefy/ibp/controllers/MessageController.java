package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.MessageRequestDTO;
import com.jefy.ibp.dtos.ResponseDTO;
import com.jefy.ibp.exceptions.OperationNotAuthorizedException;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import com.jefy.ibp.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.jefy.ibp.dtos.Constants.MESSAGES_URL;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 06/05/2024
 */
@RestController
@RequestMapping(MESSAGES_URL)
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get all the messages [For admin only]",
            description = "Fetch a page of messages",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    public ResponseEntity<ResponseDTO> getAllMessages(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ResponseDTO.builder()
                .timeStamp(LocalDateTime.now())
                .status(OK)
                .statusCode(OK.value())
                .message("messages fetched successfully")
                .data(of("messages", messageService.getAll(page,size)))
                .build()
        );
    }


    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get all the admin messages [For admin only]",
            description = "Fetch a page of messages for admins",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    public ResponseEntity<ResponseDTO> getAllMessagesForAdmins(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ResponseDTO.builder()
                .timeStamp(LocalDateTime.now())
                .status(OK)
                .statusCode(OK.value())
                .message("messages fetched successfully")
                .data(of("messages", messageService.getAllForAdmins(page,size)))
                .build()
        );
    }


    @GetMapping("/sender/{sender_id}")
    @Operation(
            summary = "Get messages by sender  [For admin or current logged user as sender only]",
            description = "Fetch a page of messages by sender using sender id",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not authorized", responseCode = "403"),
            }
    )
    public ResponseEntity<ResponseDTO> getAllMessagesForSender(
            @PathVariable("sender_id") Long senderId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        try {
            return ResponseEntity.status(OK).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("messages fetched successfully")
                    .data(of("messages", messageService.getAllBySender(senderId,page,size)))
                    .build()
            );
        }  catch (OperationNotAuthorizedException e){
            return ResponseEntity.status(FORBIDDEN).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(FORBIDDEN)
                    .statusCode(FORBIDDEN.value())
                    .message("not authorized operation")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
    }

    @GetMapping("/receiver/{receiver_id}")
    @Operation(
            summary = "Get messages by receiver [For admin or current logged user as receiver only]",
            description = "Fetch a page of messages by receiver using receiver id",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not authorized", responseCode = "403"),
            }
    )
    public ResponseEntity<ResponseDTO> getAllMessagesForReceiver(
            @PathVariable("receiver_id") Long receiverId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {

        try {
            return ResponseEntity.status(OK).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("messages fetched successfully")
                    .data(of("messages", messageService.getAllByReceiver(receiverId,page,size)))
                    .build()
            );
        } catch (OperationNotAuthorizedException e){
            return ResponseEntity.status(FORBIDDEN).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(FORBIDDEN)
                    .statusCode(FORBIDDEN.value())
                    .message("not authorized operation")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
    }



    @GetMapping("/{message_id}")
    @Operation(
            summary = "Get message by Id [For admin or current logged user as sender/receiver only]",
            description = "fetch a message using the id",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not authorized", responseCode = "403"),
                    @ApiResponse(description = "Not found", responseCode = "404"),
            }
    )
    public ResponseEntity<ResponseDTO> get(@PathVariable("message_id") Long messageId) {
        try {
            return ResponseEntity.ok(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("message fetched successfully")
                    .data(of("message", messageService.getById(messageId)))
                    .build()

            );
        } catch (RecordNotFoundException e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(NOT_FOUND)
                    .statusCode(NOT_FOUND.value())
                    .message("message not found")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        } catch (OperationNotAuthorizedException e){
            return ResponseEntity.status(FORBIDDEN).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(FORBIDDEN)
                    .statusCode(FORBIDDEN.value())
                    .message("not authorized operation")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
    }

    @PostMapping
    @Operation(
            summary = "Send a message [For current logged user as sender only]",
            description = "send a new message",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "Not authorized", responseCode = "403"),
            }
    )
    public ResponseEntity<ResponseDTO> register(@RequestBody MessageRequestDTO messageRequestDTO) {
        try {
            return ResponseEntity.ok(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(CREATED)
                    .statusCode(CREATED.value())
                    .message("message added successfully")
                    .data(of("message", messageService.create(messageRequestDTO)))
                    .build()

            );
        } catch (IllegalArgumentException | RecordNotFoundException e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .message("Could not create an new message")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        } catch (OperationNotAuthorizedException e){
            return ResponseEntity.status(FORBIDDEN).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(FORBIDDEN)
                    .statusCode(FORBIDDEN.value())
                    .message("not authorized operation")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
    }

    @PutMapping
    @Operation(
            summary = "Update a message [For current logged user as sender only]",
            description = "modifies an existing message",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "Not authorized", responseCode = "403"),
            }
    )
    public ResponseEntity<ResponseDTO> update(@RequestBody MessageRequestDTO messageRequestDTO) {
        try {
            return ResponseEntity.ok(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("message updated successfully")
                    .data(of("message", messageService.update(messageRequestDTO)))
                    .build()

            );
        } catch (IllegalArgumentException | RecordNotFoundException e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .message("Could not update an new message")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        } catch (OperationNotAuthorizedException e){
            return ResponseEntity.status(FORBIDDEN).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(FORBIDDEN)
                    .statusCode(FORBIDDEN.value())
                    .message("not authorized operation")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
    }

    @DeleteMapping("/{message_id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete a message [For admin only]",
            description = "Delete an existing message",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request/ Invalid parameter", responseCode = "400"),
            }
    )
    public ResponseEntity<ResponseDTO> delete(@PathVariable("message_id") Long messageId) {
        try {
            messageService.delete(messageId);
            return ResponseEntity.ok(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("message deleted successfully")
                    .build()

            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .message("Could not delete an new message")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
    }
}
