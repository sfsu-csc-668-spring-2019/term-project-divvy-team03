const db = require('../config/db.config.js');

export default class ActiveRecord { 
  static find( conditions ) {
    const queryString = `SELECT * FROM ${tableName()} WHERE ${conditions}`;

    db.query(queryString, (error, rows, fields) => {
      if(error) {
        throw "Something meaningful";
      }

      return rows.map(row => {
        return new this(row); // No idea if that will work
      })
    }) 
  }

  static create( values ) {
    const queryString = `INSERT INTO ${tableName()} (id, ${columns().join(',')}) VALUES (null, ${values.join(',')})`;

     db.query(queryString, (error, result) => {
      // Do something here to return data
    });
  }

  update() {

  }

  delete() {

  }
}