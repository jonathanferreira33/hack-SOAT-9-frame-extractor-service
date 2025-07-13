CREATE DATABASE IF NOT EXISTS fiapdb;
USE fiapdb;

CREATE TABLE IF NOT EXISTS video_processing_event_entity (
    id INT AUTO_INCREMENT PRIMARY KEY,
    video_path TEXT,
    video_name TEXT,
    output_dir TEXT,
    user_id TEXT,
    queued_at DATETIME,
    completed_at DATETIME,
    status VARCHAR(50)
);

INSERT INTO video_processing_event_entity (
    video_path, video_name, output_dir, user_id, queued_at, completed_at, status
) VALUES
('path/to/video1.mp4', 'video1.mp4', '/frames/output1', 'user123', NOW(), NULL, 'PROCESSING'),
('path/to/video2.mp4', 'video2.mp4', '/frames/output2', 'user123', DATE_SUB(NOW(), INTERVAL 5 MINUTE), NOW(), 'SUCCESS'),
('path/to/video3.mp4', 'video3.mp4', '/frames/output3', 'user456', DATE_SUB(NOW(), INTERVAL 10 MINUTE), NOW(), 'ERROR');
