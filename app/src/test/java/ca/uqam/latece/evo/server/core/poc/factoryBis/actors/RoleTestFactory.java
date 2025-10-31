package ca.uqam.latece.evo.server.core.poc.factoryBis.actors;



import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleTestFactory {

    @Autowired
    private RoleService roleService;

    public Role getRoleService() {
        return roleService.findByName("E-Health").getFirst();
    }

}
