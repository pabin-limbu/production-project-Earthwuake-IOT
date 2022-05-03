var mongoose = require('mongoose');
var passportLocalMongoose = require('passport-local-mongoose');
var Schema = mongoose.Schema;

let userStatusSchema = new Schema({
    respondingUser: {
        type: Schema.Types.ObjectId,
        ref: 'User',
        required: true
    },
    isSafe: {
        type: Boolean,
        required:true
    }
}, { timestamps: true });

module.exports = mongoose.model('UserStatus', userStatusSchema);