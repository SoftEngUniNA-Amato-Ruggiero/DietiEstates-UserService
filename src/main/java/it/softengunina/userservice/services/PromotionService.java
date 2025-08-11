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
    RealEstateManagerRepository managerRepository;

    PromotionService(RealEstateManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public RealEstateManager promoteUserToManager(User user, RealEstateAgency agency) {
        RealEstateManager manager = new RealEstateManager(user.getCredentials(), user.getInfo(), agency);
        managerRepository.promoteUserToAgent(user.getId(), agency.getId());
        managerRepository.promoteAgentToManager(user.getId());
        return manager;
    }
}
