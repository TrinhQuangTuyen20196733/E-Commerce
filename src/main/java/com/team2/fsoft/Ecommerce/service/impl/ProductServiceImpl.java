package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.ProductRequest;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.Product;
import com.team2.fsoft.Ecommerce.mapper.impl.ProductMapper;
import com.team2.fsoft.Ecommerce.repository.CustomProductRepository;
import com.team2.fsoft.Ecommerce.repository.ProductRepository;
import com.team2.fsoft.Ecommerce.repository.ShopRepository;
import com.team2.fsoft.Ecommerce.security.UserDetail;
import com.team2.fsoft.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ShopRepository shopRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomProductRepository customProductRepository;

    @Override
    @Transactional
    public MessagesResponse save(ProductReq productReq) {
        Product product = new Product();
        product.setName(product.getName());
        product.setDescription(product.getDescription());
        product.setCategory(categoryRepository.findByCode(productReq.getCategory()).get());
        MessagesResponse ms = new MessagesResponse();
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        var shopOptional = shopRepository.findByUserEmail(email);
        if (shopOptional.isPresent()) {
            product.setShop(shopOptional.get());
            productRepository.save(product);
            productReq.productDetailReqList.forEach(productDetailReq -> {
                ProductDetail productDetail = productDetailMapper.toEntity(productDetailReq);
                productDetail.setProduct(product);
                productDetailRepository.save(productDetail);
            });

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
            Product product = productRepository.findById(id).get();
        ms.data =  product.getProductDetailList().stream().map(productDetail ->
              new ProductRes(product.getId(),product.getName(),product.getDescription(),productDetail.getOriginPrice(),
                      productDetail.getPrice(),product.getCategory().getCode(),productDetail.getColor().getCode(),productDetail.getSize().getCode(),productDetail.getInStock(),productDetail.getSoldQuantity())).collect(Collectors.toList());


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
