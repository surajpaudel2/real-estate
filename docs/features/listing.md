# Listing Feature

## Create listing
**As a Seller/Landlord, I can create a listing with property type, price,
location, bedrooms/bathrooms, description, and photos.**

- Input: userId, propertyType, price, location, bedrooms, bathrooms,
  parkings, description, multipart photos, availability data.
- Validate the user has the SELLER role. If not, return a message
  directing them to register as a seller.
- Upload photos to S3, get back URLs, map to Photo entities.
- Persist the listing with its photos.
- Response: success + listingId.
- Notify the seller (async).

## Edit listing
**As a Seller/Landlord, I can edit my listing.**

- Input: userId, listingId, all listing fields except photos (photos are
  edited separately — see below).
- Validate: user is the seller, and the listing belongs to that same user.
- Validate field formats.
- Update, respond, notify (async).

## Deactivate listing
- Input: userId, listingId.
- Validate: user is the seller, listing belongs to that user.
- Set status → DEACTIVE.
- Respond, notify (async).

## Add photos
- Input: userId, listingId, multipart photos.
- Validate: user is the seller, listing belongs to that user, listing is
  active.
- Upload to S3, persist Photo rows with URLs.
- Respond.

## Delete photo
- Input: userId, listingId, photoId.
- Validate: user is the seller, listing belongs to that user, listing is
  active, photo belongs to that listing.
- Delete from S3 (async), remove Photo row.
- Respond.