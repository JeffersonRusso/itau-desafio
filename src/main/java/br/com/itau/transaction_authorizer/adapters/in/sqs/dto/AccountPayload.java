package br.com.itau.transaction_authorizer.adapters.in.sqs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record AccountPayload(
        UUID id,
        UUID owner,
        @JsonProperty("created_at") Long createdAt,
        String status
) {}
