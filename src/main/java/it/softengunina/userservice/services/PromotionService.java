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

    public RealEstateAgent promoteToAgent(User user, RealEstateAgency agency) {
        userRepository.delete(user);
        userRepository.flush();

        RealEstateAgent agent = new RealEstateAgent(
                user.getCredentials(),
                user.getInfo(),
                agency
        );
        agency.addAgent(agent);
        return agentRepository.save(agent);
    }

    public RealEstateManager promoteToManager(User user, RealEstateAgency agency) {
        userRepository.delete(user);
        userRepository.flush();

        RealEstateManager manager = new RealEstateManager(
                user.getCredentials(),
                user.getInfo(),
                agency
        );
        agency.addManager(manager);
        return managerRepository.save(manager);
    }
}
