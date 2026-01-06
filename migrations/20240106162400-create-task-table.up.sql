CREATE TYPE task_status AS ENUM (
    'pendente',
    'em_curso',
    'concluido'
);
--;;

CREATE TABLE task (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title TEXT NOT NULL,
    description TEXT,
    status task_status NOT NULL DEFAULT 'pendente',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);
--;;

CREATE INDEX idx_task_status ON task(status);