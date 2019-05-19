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
            return [`title LIKE '%${name}%' AND status = true ORDER BY date DESC`];
        } else if (num == 2) {
            return [`username = '${name}' ORDER BY date DESC`]
        } else if (num == 3) {
            return [`listing_id = '${name}'ORDER BY date DESC`]
        }
    }
}