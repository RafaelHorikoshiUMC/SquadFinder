ALTER TABLE perfils ADD active BOOLEAN;
UPDATE perfils SET active = true;