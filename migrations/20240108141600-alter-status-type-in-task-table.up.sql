ALTER TABLE task
ALTER COLUMN status TYPE TEXT 
USING status::text,
ALTER COLUMN status SET DEFAULT 'pendente',
ALTER COLUMN status SET NOT NULL,
ADD CONSTRAINT task_status_check
CHECK (status IN ('pendente', 'em_curso', 'concluido'));
--;;

DROP TYPE task_status;