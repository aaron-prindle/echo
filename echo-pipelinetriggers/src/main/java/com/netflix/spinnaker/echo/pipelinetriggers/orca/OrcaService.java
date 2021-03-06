/*
 * Copyright 2015 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.echo.pipelinetriggers.orca;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.netflix.spinnaker.echo.model.Pipeline;
import com.netflix.spinnaker.security.AuthenticatedRequest;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

import java.util.Collection;

public interface OrcaService {
  @POST("/orchestrate")
  Observable<TriggerResponse> trigger(@Body Pipeline pipeline);

  @POST("/orchestrate")
  Observable<TriggerResponse> trigger(@Body Pipeline pipeline, @Header(AuthenticatedRequest.SPINNAKER_USER) String runAsUser);

  @GET("/pipelines")
  Observable<Collection<PipelineResponse>> getLatestPipelineExecutions(@Query("pipelineConfigIds") Collection<String> pipelineIds);

  // GET /pipelines accepts extra query params, which is used for echo extensions.
  @GET("/pipelines")
  Observable<Collection<PipelineResponse>> getLatestPipelineExecutions(@Query("pipelineConfigIds") Collection<String> pipelineIds,
                                                                       @Query("statuses") Collection<String> statuses,
                                                                       @Query("limit") Integer limit);

  class TriggerResponse {
    private String ref;

    public TriggerResponse() {
      // do nothing
    }

    public String getRef() {
      return ref;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  class PipelineResponse {
    private String id;
    private String pipelineConfigId;
    private Long startTime;

    public PipelineResponse() {
      // do nothing
    }

    public String getId() {
      return id;
    }

    public String getPipelineConfigId() {
      return pipelineConfigId;
    }

    public Long getStartTime() {
      return startTime;
    }
  }
}
