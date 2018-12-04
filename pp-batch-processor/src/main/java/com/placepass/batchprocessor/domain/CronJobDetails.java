package com.placepass.batchprocessor.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CronDetails")
public class CronJobDetails {

    @Id
    private String id;
	
    private String vendor;

    private String cronjob;

    private boolean runnable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getCronjob() {
        return cronjob;
    }

    public void setCronjob(String cronjob) {
        this.cronjob = cronjob;
    }

    public boolean isRunnable() {
        return runnable;
    }

    public void setRunnable(boolean runnable) {
        this.runnable = runnable;
    }

}
