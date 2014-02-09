package net.siriuser.playerrider;

import org.bukkit.permissions.Permissible;

public enum Perms {
    RIDE ("ride"),

    DEBUG ("debug"),
    ;

    final String HEADER = "playerrider.";
    private String node;

    Perms(final String node) {
        this.node = HEADER + node;
    }
    public boolean has(final Permissible perm) {
        if (perm == null)
            return false;
        return perm.hasPermission(node); // only support SuperPerms
    }

    public String getNode() {
        return this.node;
    }
}
