package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.ConfirmationResponse;
import com.jefy.ibp.dtos.MessageRequest;
import com.jefy.ibp.dtos.MessageResponse;
import com.jefy.ibp.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.jefy.ibp.dtos.Constants.MESSAGES_URL;
import static org.springframework.http.HttpStatus.*;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 06/05/2024
 */
@RestController
@RequestMapping(MESSAGES_URL)
@RequiredArgsConstructor
@Tag(name = "Message")
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
    public ResponseEntity<Page<MessageResponse>> getAllMessages(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.status(OK).body(messageService.getAll(page,size));
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
    public ResponseEntity<Page<MessageResponse>> getAllMessagesForAdmins(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.status(OK).body(messageService.getAllForAdmins(page,size));
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
    public ResponseEntity<Page<MessageResponse>> getAllMessagesForSender(
            @PathVariable("sender_id") Long senderId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.status(OK).body(messageService.getAllBySender(senderId,page,size));
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
    public ResponseEntity<Page<MessageResponse>> getAllMessagesForReceiver(
            @PathVariable("receiver_id") Long receiverId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.status(OK).body(messageService.getAllByReceiver(receiverId,page,size));
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
    public ResponseEntity<MessageResponse> get(@PathVariable("message_id") Long messageId) {
        return ResponseEntity.status(OK).body(messageService.getById(messageId));
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
    public ResponseEntity<MessageResponse> register(@RequestBody @Valid MessageRequest messageRequest) {
        return ResponseEntity.status(OK).body(messageService.create(messageRequest));
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
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid MessageRequest messageRequest) {
        return ResponseEntity.status(OK).body(messageService.update(messageRequest));
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
    public ResponseEntity<ConfirmationResponse> delete(@PathVariable("message_id") Long messageId) {
        messageService.delete(messageId);
        return ResponseEntity.status(OK).body(new ConfirmationResponse("message deleted successfully"));
    }
}
