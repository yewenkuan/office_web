package com.example.office_web.entity;

import java.util.List;

public class Permission {

    private String id;

    private String permissionName;//名称.

    private String permissionEnName;

    private String resourceType;//资源类型，[menu|button]


    private List<String> operaion;//对resourceType 具备什么操作, 只有curd操作：create,update,delete,view


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public List<String> getOperaion() {
        return operaion;
    }

    public void setOperaion(List<String> operaion) {
        this.operaion = operaion;
    }

    public String getPermissionEnName() {
        return permissionEnName;
    }

    public void setPermissionEnName(String permissionEnName) {
        this.permissionEnName = permissionEnName;
    }
}
