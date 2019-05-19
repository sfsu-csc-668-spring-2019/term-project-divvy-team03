import ActiveRecord from './active-record';

export default class Users extends ActiveRecord {
    static tableName() {
        return 'user';
    }

    static columns() {
        return ['username', 'email', 'password', 'first_name', 'last_name', 'city', 'descr', 'profImage'];
    }

    // Other Listing specific logic goes here
    static findBy(name, num) {
        if (num == 1) {
            return [`username = "${name.join('" AND password = "')}"`];
        }
    }
}