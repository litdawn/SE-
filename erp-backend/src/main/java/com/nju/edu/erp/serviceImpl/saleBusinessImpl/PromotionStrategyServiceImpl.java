package com.nju.edu.erp.serviceImpl.saleBusinessImpl;

import com.nju.edu.erp.dao.saleDao.PromotionDao;
import com.nju.edu.erp.model.po.sale.PromotionPO;
import com.nju.edu.erp.service.saleBusiness.PromotionStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionStrategyServiceImpl implements PromotionStrategyService {

    private final PromotionDao promotionDao;

    @Autowired
    public PromotionStrategyServiceImpl(PromotionDao promotionDao){
        this.promotionDao=promotionDao;
    }


    @Override
    public void addVoucher(PromotionPO po) {
        promotionDao.addVoucher(po);
    }

    @Override
    public void addGift(PromotionPO po) {
        promotionDao.addGift(po);
    }

    @Override
    public void addDiscount(PromotionPO po) {
        promotionDao.addDiscount(po);
    }

    public List<PromotionPO> findAll(){
        return promotionDao.findAll();
    }

}
