#!/bin/bash

echo "=========================================="
echo "Configurando SQS Queues e DLQ..."
echo "=========================================="

# Cria a Dead Letter Queue (DLQ)
awslocal sqs create-queue \
    --queue-name conta-bancaria-criada-dlq \
    --region sa-east-1

# Cria a fila principal e atrela a DLQ a ela com 3 tentativas (maxReceiveCount=3)
awslocal sqs create-queue \
    --queue-name conta-bancaria-criada \
    --attributes '{"RedrivePolicy": "{\"deadLetterTargetArn\":\"arn:aws:sqs:sa-east-1:000000000000:conta-bancaria-criada-dlq\",\"maxReceiveCount\":\"3\"}"}' \
    --region sa-east-1

echo "=========================================="
echo "Queues configuradas com sucesso."
echo "=========================================="
