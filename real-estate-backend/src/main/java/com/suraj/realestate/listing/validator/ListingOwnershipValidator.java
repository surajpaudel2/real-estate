package com.suraj.realestate.listing.validator;

import com.suraj.realestate.common.exception.UnauthorizedActionException;
import com.suraj.realestate.listing.entity.Listing;
import org.springframework.stereotype.Component;

/**
 * Ownership authorization for listing mutations, kept separate from the
 * persistence/update logic in {@code ListingServiceImpl} so role checks
 * and business logic don't live in the same method.
 */
@Component
public class ListingOwnershipValidator {

    public void validate(Listing listing, Long sellerId) {
        if (!listing.getSeller().getId().equals(sellerId)) {
            throw new UnauthorizedActionException("You do not have permission to modify this listing.");
        }
    }
}
