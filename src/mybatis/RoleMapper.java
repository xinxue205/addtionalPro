package mybatis;

public interface RoleMapper{
    public Role getRole(Long id);
    public int deleteRole(Long id);
    public int insertRole(Role role);
}
