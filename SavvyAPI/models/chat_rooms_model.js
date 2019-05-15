import ActiveRecord from './active-record';

export default class ChatRooms extends ActiveRecord {
    static tableName() {
        return 'chat_rooms';
    }

    static columns() {
        return ['chatID, status'];
    }

    // Other Listing specific logic goes here
    static findBy(name, num) {
        if (num == 1) {
            return [`status = true`];
        }
    }
}