import ActiveRecord from './active-record';

export default class Listing extends ActiveRecord {
    static tableName() {
        return 'listing';
    }

    static columns() {
        return ['username', 'title', 'descr', 'listing_id'];
    }

    // Other Listing specific logic goes here
    static findBy(name, num) {
        if (num == 1) {
            return [`title LIKE '%${name}%' AND status = true`];
        } else if (num == 2) {
            return [`username = '${name}'`]
        }

    }
}