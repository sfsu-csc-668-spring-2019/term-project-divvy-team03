import ActiveRecord from './active-record';

export default class Chat extends ActiveRecord {
    static tableName() {
        return 'chat';
    }

    static columns() {
        return ['idchat', 'sender', 'message'];
    }

    // Other Listing specific logic goes here
    static findBy(name, num) {
        if (num == 1) {
            return [`idchat = '${name}' ORDER BY date DESC`];
        }else if(num == 2) {
         return [`sender = '${name}'`];
    }
  }
}
