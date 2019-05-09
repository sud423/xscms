package com.susd.infrastructure;

import org.apache.shiro.subject.Subject;

import com.jagregory.shiro.freemarker.PermissionTag;

public class HasAnyPermissionsTag extends PermissionTag{
	private static final String PERMISSION_NAMES_DELIMETER = ",";

    public HasAnyPermissionsTag() {
    }

    @Override
    protected boolean showTagBody(String permissions) {
        boolean hasAnyPermission = false;
        Subject subject = getSubject();
        if (subject != null) {
            for (String permission : permissions
                    .split(PERMISSION_NAMES_DELIMETER)) {
                if (subject.isPermitted(permission.trim())) {
                    hasAnyPermission = true;
                    break;
                }
            }
        }
        return hasAnyPermission;
    }
}
