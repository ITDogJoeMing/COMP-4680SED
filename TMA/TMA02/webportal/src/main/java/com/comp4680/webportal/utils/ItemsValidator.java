package com.comp4680.webportal.utils;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.comp4680.webportal.models.Items;
import com.comp4680.webportal.repos.ItemsRepo;

@Component
@SuppressWarnings("null")
public class ItemsValidator implements Validator {

    @Autowired
    private ItemsRepo repo;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Items.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Items item = (Items) target;

        if (!repo.findItemsWithSameCode(item.getId(), item.getCode()).isEmpty()) {
            errors.rejectValue("code", "items.code.duplicate", "Item code already exists");
        }

        // If the item status is sold in DB, then it should not be updated or deleted.
        if (repo.isItemsSold(item.getId()).isPresent()) {
            errors.reject("items.status.sold", "Item is already sold! Cannot be updated or deleted");
        }

        // if the items is in progress, the price should greater than that in DB
        Optional<Items> dbItemTmp = repo.findById(item.getId());
        if (dbItemTmp.isPresent()) {
            Items dbItem = dbItemTmp.get();
            if (dbItem.getStatus().equals("I") && item.getPrice().compareTo(dbItem.getPrice()) <= 0) {
                errors.rejectValue("price", "items.price.invalid",
                        "Bid price should be greater than the latest bid price");
            }
        }
    }
}