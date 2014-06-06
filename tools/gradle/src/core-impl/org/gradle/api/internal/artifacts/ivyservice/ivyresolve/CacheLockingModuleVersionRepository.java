/*
 * Copyright 2011 the original author or authors.
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
package org.gradle.api.internal.artifacts.ivyservice.ivyresolve;

import org.gradle.api.internal.artifacts.ivyservice.*;
import org.gradle.api.internal.artifacts.metadata.ComponentArtifactMetaData;
import org.gradle.api.internal.artifacts.metadata.ComponentMetaData;
import org.gradle.api.internal.artifacts.metadata.DependencyMetaData;

/**
 * A wrapper around a {@link ModuleVersionRepository} that handles locking/unlocking the cache.
 */
public class CacheLockingModuleVersionRepository implements LocalArtifactsModuleVersionRepository {
    private final LocalArtifactsModuleVersionRepository repository;
    private final CacheLockingManager cacheLockingManager;

    public CacheLockingModuleVersionRepository(LocalArtifactsModuleVersionRepository repository, CacheLockingManager cacheLockingManager) {
        this.repository = repository;
        this.cacheLockingManager = cacheLockingManager;
    }

    public String getId() {
        return repository.getId();
    }

    public String getName() {
        return repository.getName();
    }

    public void listModuleVersions(final DependencyMetaData dependency, final BuildableModuleVersionSelectionResolveResult result) {
        cacheLockingManager.longRunningOperation(String.format("List %s using repository %s", dependency, getId()), new Runnable() {
            public void run() {
                repository.listModuleVersions(dependency, result);
            }
        });
    }

    public void getDependency(final DependencyMetaData dependency, final BuildableModuleVersionMetaDataResolveResult result) {
        cacheLockingManager.longRunningOperation(String.format("Resolve %s using repository %s", dependency, getId()), new Runnable() {
            public void run() {
                repository.getDependency(dependency, result);
            }
        });
    }

    public void localResolveModuleArtifacts(ComponentMetaData component, ArtifactResolveContext context, BuildableArtifactSetResolveResult result) {
        repository.localResolveModuleArtifacts(component, context, result);
    }

    public void resolveModuleArtifacts(final ComponentMetaData component, final ArtifactResolveContext context, final BuildableArtifactSetResolveResult result) {
        cacheLockingManager.longRunningOperation(String.format("Resolve %s for %s using repository %s", context, component, getId()), new Runnable() {
            public void run() {
                repository.resolveModuleArtifacts(component, context, result);
            }
        });
    }

    public void resolveArtifact(final ComponentArtifactMetaData artifact, final ModuleSource moduleSource, final BuildableArtifactResolveResult result) {
        cacheLockingManager.longRunningOperation(String.format("Download %s using repository %s", artifact, getId()), new Runnable() {
            public void run() {
                repository.resolveArtifact(artifact, moduleSource, result);
            }
        });
    }
}
