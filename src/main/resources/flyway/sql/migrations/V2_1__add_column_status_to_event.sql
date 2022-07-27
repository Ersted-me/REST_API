ALTER TABLE events
    ADD COLUMN status VARCHAR(30) not null after action;