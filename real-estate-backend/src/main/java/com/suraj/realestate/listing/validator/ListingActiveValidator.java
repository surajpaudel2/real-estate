package com.suraj.realestate.listing.validator;

import com.suraj.realestate.listing.entity.Listing;
import com.suraj.realestate.listing.enums.ListingStatus;
import com.suraj.realestate.listing.exception.ListingNotActiveException;
import org.springframework.stereotype.Component;

/**
 * Business-state check for operations that only make sense on an ACTIVE
 * listing (adding/deleting photos), kept separate from ownership
 * authorization and from the persistence logic that uses it.
 */
@Component
public class ListingActiveValidator {

    public void validate(Listing listing) {
        if (listing.getStatus() != ListingStatus.ACTIVE) {
            throw new ListingNotActiveException("Listing must be active to perform this action.");
        }
    }
}
