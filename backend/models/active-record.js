// const db = require('../config/db.config.js');
const pool = require('../config/db.config.js');

export default class ActiveRecord {
    static find(conditions, num) {
        return this.promisify(this.findQueryString(conditions, num));
    }

    static findQueryString(conditions, num) {
        return `SELECT * FROM ${this.tableName()} WHERE ${this.findBy(conditions, num)}`;
    }

    static create(values) {
        return this.promisify(this.insertQueryString(values));
    }

    static insertQueryString(values) {
        return `INSERT INTO ${this.tableName()} (${this.columns().join(', ')}) VALUES ("${values.join('", "')}")`;
    }

    update() {

    }

    delete() {

    }

    static promisify(querystring) {
        return new Promise(function(resolve, reject) {
            pool.query(querystring, function(error, rows, fields) {
                if (error) {
                    console.log(error);
                    return reject("failed to save query");
                }
                return resolve(rows);
            })
        })
    }
}