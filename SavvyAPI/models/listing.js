import ActiveRecord from './active-record';

export default class Listing extends ActiveRecord {
  tableName() {
    return 'Listing';
  }

  columns() {
    return ['listing_name', 'listing_status', 'listing_desc', 'listing_owner'];
  }

  // Other Listing specific logic goes here
  findByListingName(name) {
    return this.find(`listing_name LIKE %${name}% AND listing_status = true`);
  }
}