var express = require('express');
var UserStatus = require('../models/userStatus');

var router = express.Router();


router.route('/')
    .get((req, res, next) => {
        UserStatus.find({})
            .then((userStatuses) => {
                res.statusCode = 200;
                res.setHeader('Content-Type', 'application/json');
                res.json(userStatuses);
            }, (err) => next(err))
            .catch((err) => next(err));
    })
    .post((req, res, next) => {
        UserStatus.create(req.body)
            .then((userStatus) => {
                res.statusCode = 200;
                res.setHeader('Content-Type', 'application/json');
                res.json(userStatus);
            }, (err) => next(err))
            .catch((err) => next(err));
    })
    .put((req, res, next) => {
        res.statusCode = 403;
        res.end('PUT operation not supported!');
    })
    .delete((req, res, next) => {
        UserStatus.deleteMany({})
            .then((reply) => {
                res.statusCode = 200;
                res.setHeader('Content-Type', 'application/json');
                res.json(reply);
            }, (err) => next(err))
            .catch((err) => next(err));
    });

router.route('/:id')
    .get((req, res, next) => {
        UserStatus.findById(req.params.id)
            .then((userStatus) => {
                res.statusCode = 200;
                res.setHeader('Content-Type', 'application/json');
                res.json(userStatus);
            }, (err) => next(err))
            .catch((err) => next(err));
    })
    .post((req, res, next) => {
        res.statusCode = 403;
        res.end("POST operation not supported!");
    })
    .put((req, res, next) => {
        UserStatus.findByIdAndUpdate(req.params.id, { $set: req.body }, { new: true, useFindAndModify: false })
            .then((userStatus) => {
                res.statusCode = 200;
                res.setHeader('Content-Type', 'application/json');
                res.json(userStatus);
            }, (err) => next(err))
            .catch((err) => next(err));
    })
    .delete((req, res, next) => {
        UserStatus.findByIdAndDelete(req.params.id)
            .then((reply) => {
                res.statusCode = 200;
                res.setHeader('Content-Type', 'application/json');
                res.json(reply);
            }, (err) => next(err))
            .catch((err) => next(err));
    });

module.exports = router;