const db = require('../config/db.config.js');


export default class ActiveRecord {
    static find(conditions, num, callback) {
        const queryString = `SELECT * FROM ${this.tableName()} WHERE ${this.findBy(conditions, num)}`;
        console.log(queryString);
        db.query(queryString, function(err, result) {
            if (err) {
                callback(err, null);
            } else {
                callback(null, JSON.stringify(result));
            }
        })
    }

    static create(values, callback) {
        console.log("at 18 on active records");
        const queryString = `INSERT INTO ${this.tableName()} (${this.columns().join(', ')}) VALUES ("${values.join('", "')}")`;
        console.log(queryString);
        db.query(queryString, function(err, result) {
            if (err) {
                console.log(err);
                callback(err, null);
            } else {
                console.log("inserted");
                callback(null, 200);
            }
        })
    }

    update(values, callback) {

    }

    delete() {

    }
}