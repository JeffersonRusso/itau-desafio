CREATE TABLE IF NOT EXISTS accounts (
    id UUID PRIMARY KEY,
    owner UUID NOT NULL,
    balance NUMERIC(38, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at BIGINT NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
    );

CREATE TABLE IF NOT EXISTS transactions (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL,
    operation_type VARCHAR(255) NOT NULL,
    amount NUMERIC(38, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_transaction_account_id ON transactions (account_id);
