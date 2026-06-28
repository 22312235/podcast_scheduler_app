package model;

import java.time.LocalDateTime;

public abstract class Episode implements Publishable {

    private String id;
    private String title;
    private int durationMinutes;
    private EpisodeStatus status;
    private LocalDateTime scheduledDateTime;

    public Episode(String id, String title, int durationMinutes) {
        this.id = id;
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.status = EpisodeStatus.DRAFT;
    }


    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public int getDurationMinutes() {
        return durationMinutes;
    }
    public EpisodeStatus getStatus() {
        return status;
    }
    public LocalDateTime getScheduledDateTime() {
        return scheduledDateTime;
    }


    protected void setStatus(EpisodeStatus status) {
        this.status = status;
    }

    protected void setScheduledDateTime(LocalDateTime dt) {
        this.scheduledDateTime = dt;
    }


    @Override
    public void schedule(LocalDateTime dateTime) {
        this.scheduledDateTime = dateTime;
        this.status = EpisodeStatus.SCHEDULED;
    }

    @Override
    public boolean canPublish(LocalDateTime now) {
        return status == EpisodeStatus.SCHEDULED
                && scheduledDateTime != null
                && !now.isBefore(scheduledDateTime);
    }

    @Override
    public void publish(LocalDateTime now) {
        if (canPublish(now)) {
            status = EpisodeStatus.PUBLISHED;
        }
    }


    public abstract String getTypeLabel();


    @Override
    public String toString() {
        String time;
        if (scheduledDateTime == null) {
            time = "Not scheduled";
        } else {
            time = scheduledDateTime.toString();
        }

        return "[" + getTypeLabel() + "] "
                + title + " (" + durationMinutes + " min) - "
                + status + " | " + time;
    }

}
