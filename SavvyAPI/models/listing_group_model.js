import ActiveRecord from './active-record';

export default class ListingGroup extends ActiveRecord {
    static tableName() {
        return 'listing_group';
    }

    static columns() {
        return ['group_id', 'username'];
    }

    // Other Listing specific logic goes here
    static findBy(name, num) {
        if (num == 1) {
            return [`group_id = '${name}'`];
        } else if (num == 2) {
            return [`username = '${name}'`]
        }
    }
}