package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.po.CandidateDetail;

public interface EnrollService {

    String candidateEnroll(String examId, CandidateDetail detail);

}
