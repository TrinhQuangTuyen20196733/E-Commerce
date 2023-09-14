package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.ProductRequest;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.dto.response.ProductDetailResponse;
import com.team2.fsoft.Ecommerce.dto.response.UserRes;
import com.team2.fsoft.Ecommerce.entity.Product;
import com.team2.fsoft.Ecommerce.entity.ProductDetail;
import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.mapper.impl.ProductDetailResponseMapper;
import com.team2.fsoft.Ecommerce.mapper.impl.ProductMapper;
import com.team2.fsoft.Ecommerce.repository.CustomProductRepository;
import com.team2.fsoft.Ecommerce.repository.ProductRepository;
import com.team2.fsoft.Ecommerce.repository.ShopRepository;
import com.team2.fsoft.Ecommerce.security.UserDetail;
import com.team2.fsoft.Ecommerce.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final CustomProductRepository customProductRepository;

    private final ProductDetailResponseMapper productDetailResponseMapper;

    public ProductServiceImpl(ProductMapper productMapper, ShopRepository shopRepository, ProductRepository productRepository, CustomProductRepository customProductRepository, ProductDetailResponseMapper productDetailResponseMapper) {
        this.productMapper = productMapper;
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
        this.customProductRepository = customProductRepository;
        this.productDetailResponseMapper = productDetailResponseMapper;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MessagesResponse save(ProductRequest productReq) {
        MessagesResponse ms = new MessagesResponse();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (UserDetail) authentication.getPrincipal();
        var userId = user.getId();
        var shopOptional = shopRepository.findByUserId(userId);
        if (shopOptional.isPresent()) {
            var product = productMapper.toEntity(productReq);
            product.setShop(shopOptional.get());
            productRepository.save(product);

        } else {
            ms.code = 500;
            ms.message = " Internal Server Error!";
        }
        return ms;
    }

    @Override
    public MessagesResponse getItems(ApiParameter apiParameter) {
        MessagesResponse ms = new MessagesResponse();

        try {
            var product = customProductRepository.getByFilter(apiParameter);
            ms.data = productMapper.toDTOList(product);
        } catch (Exception e) {
            ms.code = 404;
            ms.message = "Item Not Found!";
        }
        return ms;
    }

    @Override
    public MessagesResponse deleteById(long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            productRepository.deleteById(id);

        } catch (Exception ex) {
            ms.code = 500;
            ms.message = "Lỗi khi thao tác xóa sản phẩm. Vui lòng thử lại!";
        }
        return ms;
    }

    @Override
    public MessagesResponse getById(long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            var product = productRepository.findById(id).get();
            ms.data = productMapper.toDTO(product);
        } catch (Exception ex) {
            ms.code = 404;
            ms.message = "Item Not Found!";
        }
        return  ms;
    }

    @Override
    public List<ProductDetailResponse> getLists(ApiParameter apiParameter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductDetail> criteriaQuery = criteriaBuilder.createQuery(ProductDetail.class);
        Root<ProductDetail> root = criteriaQuery.from(ProductDetail.class);
        List<Predicate> predicates = new ArrayList<>();

        // Filter by text (if provided)
        String searchText = "%" + apiParameter.filter.text + "%";
        Predicate nameLike = criteriaBuilder.like(root.get("name"), searchText);
        Predicate colorLike = criteriaBuilder.like(root.get("color"), searchText);
        Predicate sizeLike = criteriaBuilder.like(root.get("size"), searchText);
        Predicate priceLike = criteriaBuilder.equal(root.get("price"), apiParameter.filter.text);
        predicates.add(criteriaBuilder.or(nameLike, colorLike, sizeLike, priceLike));

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

        List<ProductDetail> results = entityManager.createQuery(criteriaQuery)
                .setFirstResult((apiParameter.page - 1) * apiParameter.limit) // Offset
                .setMaxResults(apiParameter.limit) // Limit
                .getResultList();

        return productDetailResponseMapper.toDTOList(results);
    }
}
