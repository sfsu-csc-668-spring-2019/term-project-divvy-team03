import ActiveRecord from './active-record';

export default class Rating extends ActiveRecord {
    static tableName() {
        return 'rating';
    }

    static columns() {
        return ['rating', 'user_rated', 'user_rating', 'comments'];
    }

    static findBy(name, num) {
        if (num == 1) {
            return [`user_rated = '${name}' ORDER BY date DESC`]
        } else if (num == 2) {
            return [`user_rating = '${name}'ORDER BY date DESC`]
        }
    }
}