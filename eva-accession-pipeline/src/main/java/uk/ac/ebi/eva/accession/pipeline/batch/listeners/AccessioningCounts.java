/*
 * Copyright 2020 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.eva.accession.pipeline.batch.listeners;

public class AccessioningCounts {

    private long submittedVariants;

    private long discardedVariants;

    public AccessioningCounts() {
        clearCounts();
    }

    public void clearCounts() {
        this.submittedVariants = 0;
        this.discardedVariants = 0;
    }

    public long getSubmittedVariants() {
        return submittedVariants;
    }

    public void setSubmittedVariants(long submittedVariants) {
        this.submittedVariants = submittedVariants;
    }

    public void addSubmittedVariants(long submittedVariants) {
        this.submittedVariants += submittedVariants;
    }

    public long getDiscardedVariants() {
        return discardedVariants;
    }

    public void setDiscardedVariants(long discardedVariants) {
        this.discardedVariants = discardedVariants;
    }

    public void addDiscardedVariants(long discardedVariants) {
        this.discardedVariants += discardedVariants;
    }
}
