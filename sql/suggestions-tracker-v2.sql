
ALTER TABLE suggestions_tracker ALTER COLUMN suggested_to SET DEFAULT 'student';

ALTER TABLE suggestions_tracker ALTER COLUMN suggestion_criteria SET DEFAULT 'performance';

ALTER TABLE suggestions_tracker ADD COLUMN suggestion_area text NOT NULL DEFAULT 'course-map';

ALTER TABLE suggestions_tracker ADD CONSTRAINT suggestions_tracker_suggestion_area_check
    CHECK (suggestion_area = ANY (ARRAY['course-map'::text, 'class-activity'::text, 'proficiency'::text]));

ALTER TABLE suggestions_tracker ADD COLUMN tx_code text;

ALTER TABLE suggestions_tracker ADD COLUMN tx_code_type text;

ALTER TABLE suggestions_tracker ADD CONSTRAINT suggestions_tracker_tx_code_type_check
    CHECK (tx_code_type = ANY (ARRAY['competency'::text, 'micro-competency'::text, 'alt-concept'::text]));

ALTER TABLE suggestions_tracker DROP COLUMN ctx;

ALTER TABLE suggestions_tracker ADD COLUMN ca_id bigint;

ALTER TABLE suggestions_tracker ADD COLUMN path_id bigint;
    
CREATE UNIQUE INDEX st_utxcs_unq
    ON suggestions_tracker (user_id, tx_code, tx_code_type, suggested_content_id)
    WHERE tx_code is not null and tx_code_type is not null;

CREATE UNIQUE INDEX st_uccas_unq
    ON suggestions_tracker (user_id, class_id, ca_id, suggested_content_id)
    where class_id is not null and ca_id is not null;
