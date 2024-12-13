/**
 * Copyright © 2016-2024 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.server.common.data.cf;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.thingsboard.server.common.data.BaseData;
import org.thingsboard.server.common.data.ExportableEntity;
import org.thingsboard.server.common.data.HasName;
import org.thingsboard.server.common.data.HasTenantId;
import org.thingsboard.server.common.data.HasVersion;
import org.thingsboard.server.common.data.cf.configuration.CalculatedFieldConfiguration;
import org.thingsboard.server.common.data.cf.configuration.SimpleCalculatedFieldConfiguration;
import org.thingsboard.server.common.data.id.CalculatedFieldId;
import org.thingsboard.server.common.data.id.EntityId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.validation.Length;
import org.thingsboard.server.common.data.validation.NoXss;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class CalculatedField extends BaseData<CalculatedFieldId> implements HasName, HasTenantId, HasVersion, ExportableEntity<CalculatedFieldId> {

    private static final long serialVersionUID = 4491966747773381420L;

    private TenantId tenantId;
    private EntityId entityId;

    @NoXss
    @Length(fieldName = "type")
    private CalculatedFieldType type;
    @NoXss
    @Length(fieldName = "name")
    @Schema(description = "User defined name of the calculated field.")
    private String name;
    @Schema(description = "Version of calculated field configuration.", example = "0")
    private int configurationVersion;
    @Schema(implementation = SimpleCalculatedFieldConfiguration.class)
    private transient CalculatedFieldConfiguration configuration;
    @Getter
    @Setter
    private Long version;
    @Getter
    @Setter
    private CalculatedFieldId externalId;

    public CalculatedField() {
        super();
    }

    public CalculatedField(CalculatedFieldId id) {
        super(id);
    }

    public CalculatedField(TenantId tenantId, EntityId entityId, CalculatedFieldType type, String name, int configurationVersion, CalculatedFieldConfiguration configuration, Long version, CalculatedFieldId externalId) {
        this.tenantId = tenantId;
        this.entityId = entityId;
        this.type = type;
        this.name = name;
        this.configurationVersion = configurationVersion;
        this.configuration = configuration;
        this.version = version;
        this.externalId = externalId;
    }

    @Schema(description = "JSON object with the Calculated Field Id. Referencing non-existing Calculated Field Id will cause error.")
    @Override
    public CalculatedFieldId getId() {
        return super.getId();
    }

    @Schema(description = "Timestamp of the calculated field creation, in milliseconds", example = "1609459200000", accessMode = Schema.AccessMode.READ_ONLY)
    @Override
    public long getCreatedTime() {
        return super.getCreatedTime();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("CalculatedField[")
                .append("tenantId=").append(tenantId)
                .append(", entityId=").append(entityId)
                .append(", type='").append(type)
                .append(", name='").append(name)
                .append(", configurationVersion=").append(configurationVersion)
                .append(", configuration=").append(configuration)
                .append(", version=").append(version)
                .append(", externalId=").append(externalId)
                .append(", createdTime=").append(createdTime)
                .append(", id=").append(id).append(']')
                .toString();
    }

}
