/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.core.room.service.impl;


import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;
import org.kuali.student.r2.core.room.service.RoomService;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class RoomServiceMockImplNew implements RoomService
{

    @Override
    public RoomInfo getRoom(String roomId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.roomMap.containsKey(roomId)) {
            throw new DoesNotExistException(roomId);
        }
        return new RoomInfo(this.roomMap.get (roomId));
    }

    @Override
    public List<RoomInfo> getRoomsByIds(List<String> roomIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<RoomInfo> list = new ArrayList<RoomInfo> ();
        for (String id: roomIds) {
            list.add (this.getRoom(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getRoomIdsByBuilding(String buildingId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (RoomInfo info: roomMap.values ()) {
            if (buildingId.equals(info.getBuildingId())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<String> getRoomIdsByBuildingAndFloor(String buildingId, String floor, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (RoomInfo info: roomMap.values ()) {
            if (buildingId.equals(info.getBuildingId())) {
                if (floor.equals(info.getFloor())) {
                    list.add (info.getId ());
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getRoomIdsByType(String roomTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (RoomInfo info: roomMap.values ()) {
            if (roomTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<String> getRoomIdsByBuildingAndRoomType(String buildingId, String roomTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (RoomInfo info: roomMap.values ()) {
            if (buildingId.equals(info.getBuildingId())) {
                if (roomTypeKey.equals(info.getTypeKey())) {
                    list.add (info.getId ());
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getRoomsByBuildingAndRoomUsageTypes(String buildingId, List<String> roomUsageTypeKeys, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForRoomIds has not been implemented");
    }

    @Override
    public List<String> getRoomIdsByBuildingAndRoomTypes(String buildingId, List<String> roomTypeKeys, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (RoomInfo info: roomMap.values ()) {
            if (buildingId.equals(info.getBuildingId())) {
                if (roomTypeKeys.equals(info.getTypeKey())) {
                    list.add (info.getId ());
                }
            }
        }
        return list;
    }

    @Override
    public List<String> searchForRoomIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForRoomIds has not been implemented");
    }

    @Override
    public List<RoomInfo> searchForRooms(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForRooms has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateRoom(String validationTypeKey, String buildingId, String roomTypeKey, RoomInfo roomInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        return new ArrayList<ValidationResultInfo> ();
    }
    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, RoomInfo> roomMap = new LinkedHashMap<String, RoomInfo>();

    @Override
    public RoomInfo createRoom(String buildingId, String roomTypeKey, RoomInfo roomInfo, ContextInfo contextInfo)
            throws AlreadyExistsException
            ,DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // create
        if (!roomTypeKey.equals (roomInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        RoomInfo copy = new RoomInfo(roomInfo);
        if (copy.getId() == null) {
            copy.setId(roomMap.size() + "");
        }
        copy.setMeta(newMeta(contextInfo));
        roomMap.put(copy.getId(), copy);
        return new RoomInfo(copy);
    }

    @Override
    public RoomInfo updateRoom(String roomId, RoomInfo roomInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // update
        if (!roomId.equals (roomInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        RoomInfo copy = new RoomInfo(roomInfo);
        RoomInfo old = this.getRoom(roomInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.roomMap .put(roomInfo.getId(), copy);
        return new RoomInfo(copy);
    }

    @Override
    public StatusInfo deleteRoom(String roomId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (this.roomMap.remove(roomId) == null) {
            throw new DoesNotExistException(roomId);
        }
        return newStatus();
    }

    @Override
    public BuildingInfo getBuilding(String buildingId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.buildingMap.containsKey(buildingId)) {
            throw new DoesNotExistException(buildingId);
        }
        return new BuildingInfo(this.buildingMap.get (buildingId));
    }

    @Override
    public List<BuildingInfo> getBuildingsByIds(List<String> buildingIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<BuildingInfo> list = new ArrayList<BuildingInfo> ();
        for (String id: buildingIds) {
            list.add (this.getBuilding(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getBuildingIdsByCampus(String campusKey, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (BuildingInfo info: buildingMap.values ()) {
            if (campusKey.equals(info.getCampusKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForBuildingIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForBuildingIds has not been implemented");
    }

    @Override
    public List<BuildingInfo> searchForBuildings(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForBuildings has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateBuilding(String buildingTypeKey, String validationTypeKey, BuildingInfo buildingInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        return new ArrayList<ValidationResultInfo> ();
    }
    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, BuildingInfo> buildingMap = new LinkedHashMap<String, BuildingInfo>();

    @Override
    public BuildingInfo createBuilding(String buildingTypeKey, BuildingInfo buildingInfo, ContextInfo contextInfo)
            throws AlreadyExistsException
            ,DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // create
        if (!buildingTypeKey.equals (buildingInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        BuildingInfo copy = new BuildingInfo(buildingInfo);
        if (copy.getId() == null) {
            copy.setId(buildingMap.size() + "");
        }
        copy.setMeta(newMeta(contextInfo));
        buildingMap.put(copy.getId(), copy);
        return new BuildingInfo(copy);
    }

    @Override
    public BuildingInfo updateBuilding(String buildingId, BuildingInfo buildingInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // update
        if (!buildingId.equals (buildingInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        BuildingInfo copy = new BuildingInfo(buildingInfo);
        BuildingInfo old = this.getBuilding(buildingInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.buildingMap .put(buildingInfo.getId(), copy);
        return new BuildingInfo(copy);
    }

    @Override
    public StatusInfo deleteBuilding(String buildingId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (this.buildingMap.remove(buildingId) == null) {
            throw new DoesNotExistException(buildingId);
        }
        return newStatus();
    }

    @Override
    public RoomResponsibleOrgInfo getRoomResponsibleOrg(String roomResponsibleOrgId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.roomResponsibleOrgMap.containsKey(roomResponsibleOrgId)) {
            throw new DoesNotExistException(roomResponsibleOrgId);
        }
        return new RoomResponsibleOrgInfo(this.roomResponsibleOrgMap.get (roomResponsibleOrgId));
    }

    @Override
    public List<RoomResponsibleOrgInfo> getRoomResponsibleOrgsByIds(List<String> roomResponsibleOrgIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<RoomResponsibleOrgInfo> list = new ArrayList<RoomResponsibleOrgInfo> ();
        for (String id: roomResponsibleOrgIds) {
            list.add (this.getRoomResponsibleOrg(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getRoomResponsibleOrgIdsByType(String roomResponsibleOrgTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (RoomResponsibleOrgInfo info: roomResponsibleOrgMap.values ()) {
            if (roomResponsibleOrgTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<String> getRoomResponsibleOrgIdsByRoom(String roomId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (RoomResponsibleOrgInfo info: roomResponsibleOrgMap.values ()) {
            if (roomId.equals(info.getRoomId())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<String> getRoomResponsibleOrgIdsForBuilding(String buildingId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("getRoomResponsibleOrgIdsForBuilding has not been implemented");
    }

    @Override
    public List<String> searchForRoomResponsibleOrgIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForRoomResponsibleOrgIds has not been implemented");
    }

    @Override
    public List<RoomResponsibleOrgInfo> searchForRoomResponsibleOrgs(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForRoomResponsibleOrgs has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateRoomResponsibleOrg(String validationTypeKey, String roomId, String orgId, String roomResponsibleOrgTypeKey, RoomResponsibleOrgInfo roomResponsibleOrgInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        return new ArrayList<ValidationResultInfo> ();
    }
    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, RoomResponsibleOrgInfo> roomResponsibleOrgMap = new LinkedHashMap<String, RoomResponsibleOrgInfo>();

    @Override
    public RoomResponsibleOrgInfo createRoomResponsibleOrg(String roomId, String orgId, String roomResponsibleOrgTypeKey, RoomResponsibleOrgInfo roomResponsibleOrgInfo, ContextInfo contextInfo)
            throws AlreadyExistsException
            ,DataValidationErrorException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // create
        if (!roomResponsibleOrgTypeKey.equals (roomResponsibleOrgInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        RoomResponsibleOrgInfo copy = new RoomResponsibleOrgInfo(roomResponsibleOrgInfo);
        if (copy.getId() == null) {
            copy.setId(roomResponsibleOrgMap.size() + "");
        }
        copy.setMeta(newMeta(contextInfo));
        roomResponsibleOrgMap.put(copy.getId(), copy);
        return new RoomResponsibleOrgInfo(copy);
    }

    @Override
    public RoomResponsibleOrgInfo updateRoomResponsibleOrg(String roomResponsibleOrgId, RoomResponsibleOrgInfo roomResponsibleOrgInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // update
        if (!roomResponsibleOrgId.equals (roomResponsibleOrgInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        RoomResponsibleOrgInfo copy = new RoomResponsibleOrgInfo(roomResponsibleOrgInfo);
        RoomResponsibleOrgInfo old = this.getRoomResponsibleOrg(roomResponsibleOrgInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.roomResponsibleOrgMap .put(roomResponsibleOrgInfo.getId(), copy);
        return new RoomResponsibleOrgInfo(copy);
    }

    @Override
    public StatusInfo deleteRoomResponsibleOrg(String roomResponsibleOrgId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (this.roomResponsibleOrgMap.remove(roomResponsibleOrgId) == null) {
            throw new DoesNotExistException(roomResponsibleOrgId);
        }
        return newStatus();
    }

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo newMeta(ContextInfo context) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }

}

