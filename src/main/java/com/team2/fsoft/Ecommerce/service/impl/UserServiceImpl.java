package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.RegisterReq;
import com.team2.fsoft.Ecommerce.dto.response.UserRes;
import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.mapper.impl.UserMapper;
import com.team2.fsoft.Ecommerce.mapper.impl.UserResMapper;
import com.team2.fsoft.Ecommerce.repository.UserRepository;
import com.team2.fsoft.Ecommerce.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @PersistenceContext
    private EntityManager entityManager;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final UserResMapper userResMapper;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,UserResMapper userResMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userResMapper =userResMapper;
    }

    @Override
    public User create(RegisterReq registerReq) {
        return userRepository.save(userMapper.toEntity(registerReq));
    }

    @Override
    public void updateUserInformation(@Valid RegisterReq registerReq) {
        userRepository.save(userMapper.toEntity(registerReq));

    }

    @Override
    public void deleteUser(long id) {

        userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        }
        throw new RuntimeException("Email doesn't exist");
    }

    @Override
    public List<UserRes> getLists(ApiParameter apiParameter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        // Filter by text (if provided)
        String searchText = "%" + apiParameter.filter.text + "%";
        Predicate nameLike = criteriaBuilder.like(root.get("name"), searchText);
        Predicate emailLike = criteriaBuilder.like(root.get("email"), searchText);
        predicates.add(criteriaBuilder.or(nameLike, emailLike));

        // Filter by created date (if provided)
        if (apiParameter.filter != null && apiParameter.filter.created != null) {
            predicates.add(criteriaBuilder.equal(root.get("created"), apiParameter.filter.created));
        }

        // Filter by author (if provided)
        if (apiParameter.filter != null && apiParameter.filter.author != null && !apiParameter.filter.author.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("author"), apiParameter.filter.author));
        }
        // Filter by descending and orderBy (if provided)
        if (apiParameter.filter != null && apiParameter.filter.orderBy!=null) {
            if (apiParameter.filter.ascending) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(apiParameter.filter.orderBy)));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(apiParameter.filter.orderBy)));
            }
        }

        if (!predicates.isEmpty()) {

            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }
        List<User> results = entityManager.createQuery(criteriaQuery)
                .setFirstResult((apiParameter.page - 1) * apiParameter.limit) // Offset
                .setMaxResults(apiParameter.limit) // Limit
                .getResultList();

            return userResMapper.toDTOList(results);
        }

        @Override
        public void changePassword (String email, String oldPassword, String newPassword){
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                var user = userOptional.get();
                if (user.getPassword().equals(oldPassword)) {
                    user.setPassword(newPassword);
                    userRepository.save(user);

                }
            }

        }
    }
