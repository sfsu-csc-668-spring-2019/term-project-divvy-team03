// const db = require('../config/db.config.js');

/* This goes in db.config.js */
const promisify = fn => querystring => {
    fn(querystring, (error, result) => {
        if(error) {
            return Promise.reject(error);
        } else {
            return Promise.resolve(result);
        }
    })
}

module.exports = query
/* End db.config.js */
const query = require('../config/db.config.js');

export default class ActiveRecord {
    static find(conditions, num) {
        return query(findQueryString(conditions,num));
    }

    static findQueryString(conditions, num) {
        return `SELECT * FROM ${this.tableName()} WHERE ${this.findBy(conditions, num)}`;
    }

    static create(values) {
        return query(insertQueryString(values));
    }

    static insertQueryString(values) {
        return `INSERT INTO ${this.tableName()} (${this.columns().join(', ')}) VALUES ("${values.join('", "')}")`;
    }

    update(values, callback) {

    }

    delete() {

    }
}