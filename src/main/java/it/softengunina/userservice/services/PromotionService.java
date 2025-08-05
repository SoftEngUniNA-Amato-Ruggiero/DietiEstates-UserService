package it.softengunina.userservice.services;

import it.softengunina.userservice.model.RealEstateAgency;
import it.softengunina.userservice.model.RealEstateAgent;
import it.softengunina.userservice.model.RealEstateManager;
import it.softengunina.userservice.model.User;
import it.softengunina.userservice.repository.RealEstateAgentRepository;
import it.softengunina.userservice.repository.RealEstateManagerRepository;
import it.softengunina.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PromotionService {
    UserRepository<User> userRepository;
    RealEstateAgentRepository<RealEstateAgent> agentRepository;
    RealEstateManagerRepository managerRepository;

    PromotionService(UserRepository<User> userRepository,
                     RealEstateAgentRepository<RealEstateAgent> agentRepository,
                     RealEstateManagerRepository managerRepository) {
        this.userRepository = userRepository;
        this.agentRepository = agentRepository;
        this.managerRepository = managerRepository;
    }

    public RealEstateAgent promoteUserToAgent(User user, RealEstateAgency agency) {
        RealEstateAgent agent = new RealEstateAgent(
                user.getCredentials(),
                user.getInfo(),
                agency
        );
        agency.addAgent(agent);

//        userRepository.delete(user);
//        userRepository.flush();
//        return agentRepository.save(agent);

        agentRepository.promoteUserToAgent(user.getId(), agency.getId());
        agentRepository.flush();
        return agent;
    }

    public RealEstateManager promoteUserToManager(User user, RealEstateAgency agency) {
        RealEstateManager manager = new RealEstateManager(
                user.getCredentials(),
                user.getInfo(),
                agency
        );
        agency.addManager(manager);

//        userRepository.delete(user);
//        userRepository.flush();
//        return managerRepository.save(manager);

        managerRepository.promoteUserToAgent(user.getId(), agency.getId());
        managerRepository.promoteAgentToManager(user.getId());
        managerRepository.flush();
        return manager;
    }
}
