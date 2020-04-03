package com.redhat.cajun.navy.datawarehouse.dao;

import java.util.concurrent.CompletionStage;

import com.redhat.cajun.navy.datawarehouse.model.MissionReport;

public interface IReportingDAO {

    // return 1 if persisted, 0 if not persisted
    public CompletionStage<Integer> persistMissionReport(MissionReport missionReport);

    // return number of records deleted
    public CompletionStage<Integer> flushMissionReportTable();

}
