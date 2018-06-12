/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples;

import com.google.api.services.jobs.v2.JobService;
import com.google.api.services.jobs.v2.model.JobQuery;
import com.google.api.services.jobs.v2.model.LocationFilter;
import com.google.api.services.jobs.v2.model.MatchingJob;
import com.google.api.services.jobs.v2.model.RequestMetadata;
import com.google.api.services.jobs.v2.model.SearchJobsRequest;
import com.google.api.services.jobs.v2.model.SearchJobsResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Cloud Job Discovery Search Basics.
 */
public final class SearchBasicSamples {

  private static JobService jobService = JobServiceUtils.getJobService();

  // [START example_1]

  /**
   * Basic keyword search.
   */
  public static void keywordSearch() throws IOException {
    // Make sure to set the requestMetadata the same as the associated search request
    RequestMetadata requestMetadata =
        new RequestMetadata()
            // Make sure to hash your userID
            .setUserId("HashedUserId")
            // Make sure to hash the sessionID
            .setSessionId("HashedSessionID")
            // Domain of the website where the search is conducted
            .setDomain(
                "www.google.com");

    // Perform a search for analyst related jobs
    JobQuery jobQuery = new JobQuery().setQuery("analyst");

    SearchJobsRequest request =
        new SearchJobsRequest()
            .setRequestMetadata(requestMetadata)
            // Set the actual search term as defined in the jobQurey
            .setQuery(jobQuery)
            // Set the search mode to a regular search
            .setMode("JOB_SEARCH");

    SearchJobsResponse response = jobService.jobs().search(request).execute();

    if (response.getMatchingJobs() != null) {
      for (MatchingJob match : response.getMatchingJobs()) {
        System.out.println(match.getJob().getJobTitle());
        System.out.println(match.getJob().getName());
        System.out.println(match.getJobSummary());
      }
    } else {
      System.out.println("No jobs for this search");
    }
  }
  // [END example_1]

  // [START example_2]

  /**
   * Keyword and single location search.
   */
  public static void keywordAndSingleLocationSearch() throws IOException {
    // Make sure to set the requestMetadata the same as the associated search request
    RequestMetadata requestMetadata =
        new RequestMetadata()
            // Make sure to hash your userID
            .setUserId("HashedUserId")
            // Make sure to hash the sessionID
            .setSessionId("HashedSessionID")
            // This is the domain of the website on which the search is
            .setDomain(
                "www.google.com");
    // conducted
    LocationFilter locationFilter =
        new LocationFilter()
            .setName("1600 Amphitheatre Parkway, Mountain View, CA")
            .setDistanceInMiles(0.5D);
    JobQuery jobQuery =
        new JobQuery()
            .setQuery("Software Engineer")
            .setLocationFilters(Arrays.asList(locationFilter));
    SearchJobsRequest request =
        new SearchJobsRequest()
            .setRequestMetadata(requestMetadata)
            .setQuery(jobQuery)
            .setMode("JOB_SEARCH");
    SearchJobsResponse response = jobService.jobs().search(request).execute();
    System.out.println(response);
  }
  // [END example_2]

  // [START example_3]

  /**
   * Keyword and multiple locations search.
   */
  public static void keywordAndMultiLocationsSearch() throws IOException {
    // Make sure to set the requestMetadata the same as the associated search request
    RequestMetadata requestMetadata =
        new RequestMetadata()
            // Make sure to hash your userID
            .setUserId("HashedUserId")
            // Make sure to hash the sessionID
            .setSessionId("HashedSessionID")
            // Domain of the website where the search is conducted
            .setDomain(
                "www.google.com");
    List<LocationFilter> locationFilters =
        Arrays.asList(
            new LocationFilter().setName("Mountain View, CA"),
            new LocationFilter().setName("Sunnyvale, CA"));
    JobQuery jobQuery = new JobQuery().setQuery("Analyst")
        .setLocationFilters(locationFilters);
    SearchJobsRequest request =
        new SearchJobsRequest()
            .setRequestMetadata(requestMetadata)
            .setQuery(jobQuery)
            .setMode("JOB_SEARCH");
    SearchJobsResponse response = jobService.jobs().search(request).execute();
    System.out.println(response);
  }
  // [END example_3]

  // [START example_4]

  /**
   * Keyword and multiple employment types search.
   */
  public static void keywordAndMultiEmploymentTypesSearch() throws IOException {
    // Make sure to set the requestMetadata the same as the associated search request
    RequestMetadata requestMetadata =
        new RequestMetadata()
            // Make sure to hash your userID
            .setUserId("HashedUserId")
            // Make sure to hash the sessionID
            .setSessionId("HashedSessionID")
            // This is the domain of the website on which the search is
            .setDomain("www.google.com");
    // conducted
    JobQuery jobQuery =
        new JobQuery().setQuery("Analyst")
            .setEmploymentTypes(Arrays.asList("FULL_TIME", "INTERN"));
    SearchJobsRequest request =
        new SearchJobsRequest()
            .setRequestMetadata(requestMetadata)
            .setQuery(jobQuery)
            .setMode("JOB_SEARCH");
    SearchJobsResponse response = jobService.jobs().search(request).execute();
    System.out.println(response);
  }
  // [END example_1]

  public static void main(String... args) throws Exception {
    keywordSearch();
    keywordAndSingleLocationSearch();
    keywordAndMultiLocationsSearch();
    keywordAndMultiEmploymentTypesSearch();
  }
}
