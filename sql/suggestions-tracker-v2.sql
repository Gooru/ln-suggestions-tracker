
ALTER TABLE suggestions_tracker ALTER COLUMN suggested_to SET DEFAULT 'student';

ALTER TABLE suggestions_tracker ALTER COLUMN suggestion_criteria SET DEFAULT 'performance';

ALTER TABLE suggestions_tracker ADD COLUMN suggestion_area text NOT NULL DEFAULT 'course-map';

ALTER TABLE suggestions_tracker ADD CONSTRAINT suggestions_tracker_suggestion_area_check
    CHECK (suggestion_area = ANY (ARRAY['course-map'::text, 'class-activity'::text, 'proficiency'::text]));

ALTER TABLE suggestions_tracker ADD COLUMN tx_code text;

ALTER TABLE suggestions_tracker ADD COLUMN tx_code_type text;

ALTER TABLE suggestions_tracker ADD CONSTRAINT suggestions_tracker_tx_code_type_check
    CHECK (suggestion_area = ANY (ARRAY['competency'::text, 'micro-competency'::text, 'alt-concept'::text]));

ALTER TABLE suggestions_tracker DROP COLUMN ctx;


