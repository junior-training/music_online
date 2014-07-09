KISSY.add(function (S, Node, Anim, XTemplate, IO, dd, ListTpl, TypeTpl) {
    var $ = S.all;

    function Player(container, options) {
        var self = this;
        this.el = $(container);
        this.audio = this.el.one('audio')[0];
        this.volume = 0.5;
        this.animImg = $('.rotate-img');
        this.pageIndex = {
            chinese: 0,
            western: 0,
            jk: 0,
            rank: 0
        };
        this.hintIndex = -1;
        this.musicData = {};
        this.mList = [];
        this.lrc = [];
        this.defaltImg = 'resource/img/default.jpg';
        this.currentPlay = 0;
        this.animStyle = {
            transform: 'rotate(360deg)'
        };
        this.animCfg = {
            duration: 15,
            easing: 'easeNone',
            complete: function () {
                self.animImg.css('transform', 'rotate(0deg)');
                self.animCD = new Anim(self.animImg, self.animStyle, self.animCfg);
                self.animCD.run();
            }
        };
        this.animCD = new Anim(this.animImg, this.animStyle, this.animCfg);
        this.typeListenTimes = 0;
    }

    Player.prototype.init = function () {
        var self = this,
            audioSrc,
            animImgSrc;
        this.musicList();
        if (this.mList.length === 0) {
            audioSrc = '';
            animImgSrc = this.defaltImg;
        } else {
            $($('.p-info a')[0]).text(this.mList[this.currentPlay].title);
            $($('.p-info a')[1]).text(this.mList[this.currentPlay].artist);
            $($('.p-info a')[2]).text(this.mList[this.currentPlay].album);
            audioSrc = this.mList[this.currentPlay].src;
            animImgSrc = this.mList[this.currentPlay].img;
        }
        $(this.audio).attr('src', audioSrc);
        this.searchHandler();
        this.initPanel();
        this.animImg.css('background-image', 'url("' + animImgSrc + '")');
        $('.m-list .list-item:nth-child(' + (this.currentPlay + 1) + ')').addClass('playing');

        var showListBtn = $('.show-list'),
            showTypeBtn = $('.show-type');
        var musicListEl = $('.music-list'),
            listShown = false,
            typeWrap = $('.type-wrap'),
            typeShown = false;
        var gutter = $('.gutter'),
            timeInfo = $('.time-info');
        showListBtn.on('click', function () {
            if (listShown) {
                musicListEl.css('transform', 'translate3d(-300px,0,0)');
                $(this).css({
                    width: '40px',
                    border: 'none'
                }).text('>');
                listShown = false;
            } else {
                musicListEl.css('transform', 'translate3d(300px,0,0)');
                $(this).css({
                    width: '200px',
                    border: '1px solid'
                }).text('< 正在播放');
                listShown = true;
            };
        });

        showTypeBtn.on('click', function () {
            if (typeShown) {
                typeWrap.css('transform', 'translate3d(300px, 0, 0)');
                $('.volume').css('right', '100px');
                $(this).css({
                    width: '40px',
                    border: 'none'
                }).text('<');
                typeShown = false;
            } else {
                typeWrap.css('transform', 'translate3d(-300px, 0, 0)');
                $('.volume').css('right', '300px');
                $(this).css({
                    width: '200px',
                    border: '1px solid'
                }).text('到处看看 >');
                typeShown = true;
            };
        });

        $(self.audio).on('timeupdate', this.handleTimeUpdate(self));

        gutter.on('mouseenter', function (ev) {
            if (self.audio.currentTime === 0)
                return;
            timeInfo.css({
                top: ev.pageY + 10,
                left: ev.pageX + 10,
                display: 'block'
            });
            gutter.on('mousemove', function (ev) {
                timeInfo.css({
                    top: ev.pageY + 10,
                    left: ev.pageX + 10
                });
                ev.halt();
            });
        });
        gutter.on('mouseleave', function (ev) {
            timeInfo.css({
                display: 'none'
            });
            gutter.detach('mousemove');
        });
        gutter.on('click', function (ev) {
            if (self.audio.currentTime === undefined)
                return;
            self.audio.currentTime = (Math.floor(ev.pageX / $(this).width() * 1000 + 3) / 1000) * Math.floor(self.audio.duration);
        });

        this.handleDetail();

        new IO({
            type: "get",
            //            url: "music-data.js",
            url: "displayTop10Songs",
            success: function (data) {
                self.musicData = data;
                self.initTeyeWrap(data);
            },
            error: function (m, io) {
                console.log(m);
            },
            dataType: "json"
        });

        this.handleVol();

        this.makeLrc();

        new dd.Draggable({
            'node': '.handler',
        });
        new dd.Draggable({
            'node': '.vol-handler'
        });
        dd.DDM.on('drag', function (ev) {
            var node = $(ev.drag.userConfig.node);
            if (node.hasClass('handler'))
                node.css('left', ev.pageX - 15);
            if (node.hasClass('vol-handler')) {
                var posTop = ev.pageY - node.parent().offset().top - 5;
                if (posTop < 0)
                    posTop = 0;
                if (posTop > 100)
                    posTop = 100;
                node.css('top', posTop);
            }
        });
    };

    Player.prototype.handleTimeUpdate = function (self) {

        return function () {
            var handler = $('.handler'),
                handPos = 0,
                gutter = $('.gutter'),
                timeInfo = $('.time-info');
            var i = 2,
                cm = Math.floor(this.currentTime / 60),
                cs = Math.floor(this.currentTime % 60),
                dm = Math.floor(this.duration / 60),
                ds = Math.floor(this.duration % 60);

            handPos = this.currentTime / this.duration * 100;
            handler.css('left', handPos - 1 + '%');
            if (this.ended)
                self.playOther(self.currentPlay + 1);

            $('.time-info').text(cm + ':' + cs + '/' + dm + ':' + ds);

            if (self.lrc.length === 0 ||
                this.currentTime < self.lrc[2].timeline)
                return;
            while (!(this.currentTime >= self.lrc[i].timeline && this.currentTime < self.lrc[i + 1].timeline)) {
                i++;
                if (i >= self.lrc.length - 1)
                    return;
            }
            $('.lrc-text').css({
                transform: 'translate3D(0, ' + (-18 * (i - 1)) + 'px, 0)'
            });
        }
    }

    Player.prototype.initPanel = function () {
        var self = this,
            toggleBtn = $('.toggle'),
            otherBtn = $('.otherBtn'),
            forwardBtn = $('.forward'),
            backwardBtn = $('.backward'),
            nextBtn = $('.next'),
            preBtn = $('.pre');

        toggleBtn.on('click', function () {
            if (self.audio.paused) {
                if (self.mList.length === 0)
                    return;
                self.audio.play();
                self.animCD.isPaused() ? self.animCD.resume() : self.animCD.run();
                $(this).css('background-position', '-264px -3px');
            } else {
                self.audio.pause();
                self.animCD.pause();
                $(this).css('background-position', '-34px -44px');
            };
        });

        toggleBtn.on('mouseenter', function () {
            $(this).siblings().addClass('p-show');
        });

        otherBtn.on('mouseleave', function () {
            $(this).removeClass('p-show');
        });

        forwardBtn.on('click', function () {
            self.audio.currentTime += 5;
        });

        backwardBtn.on('click', function () {
            self.audio.currentTime -= 5;
        });

        nextBtn.on('click', function () {
            self.playOther(self.currentPlay + 1);
        });

        preBtn.on('click', function () {
            self.playOther(self.currentPlay - 1);
        });
    }

    Player.prototype.handleVol = function () {
        var self = this,
            volEl = $('.volume'),
            volIcon = $('.volIcon'),
            volBar = $('.volBar'),
            barWrap = $('.barWrap'),
            volHandler = $('.vol-handler');

        volIcon.on('mouseenter', function (ev) {
            barWrap.css('transform', 'rotateX(0)');
            ev.halt();
        });
        volEl.on('mouseleave', function (ev) {
            barWrap.css('transform', 'rotateX(90deg)');
            ev.halt();
        });

        volIcon.on('click', function (ev) {
            $(this).toggleClass('mute');
            if (self.audio.volume === 0) {
                self.audio.volume = self.volume;
                volHandler.css('top', (100 - self.volume * 100 - 1) + '%');
            } else {
                self.audio.volume = 0;
                volHandler.css('top', '99%');

            }
            ev.halt();
        });

        volBar.on('click', function (ev) {
            volHandler.css('top', (ev.pageY - volBar.offset().top - 5) + 'px');
            var v = (volHandler.offset().top - volBar.offset().top) / 100;
            if (v < 0)
                v = 0;
            else if (v > 1)
                v = 1;
            self.audio.volume = self.volume = 1 - v;
            if (self.audio.volume === 0)
                volIcon.addClass('mute');
            else
                volIcon.removeClass('mute');
        });
    }

    Player.prototype.getPlaylist = function () {
        var list = [];
        list = JSON.parse(localStorage.getItem('playlist'));
        return list || [];
    }

    Player.prototype.musicList = function () {
        this.mList = this.getPlaylist();
        var listEl = $('.m-list'),
            list = [],
            self = this;
        for (var i = 0; i < this.mList.length; i++) {
            list.push(new XTemplate(ListTpl).render(this.mList[i]));
        };
        var listStr = list.join('\n');
        listEl.append(listStr);

        listEl.delegate('click', '.m-list .list-item', function (ev) {
            self.playOther($(ev.currentTarget).index());
            $('.toggle').css('background-position', '-264px -3px');
            ev.halt();
        });

        listEl.delegate('click', '.m-operate .m-delete', function (ev) {
            var el = $(ev.currentTarget).parent().parent(),
                index = el.index();
            self.mList.splice(index, 1);
            localStorage.setItem('playlist', JSON.stringify(self.mList));
            el.remove();
            if (index < self.currentPlay) {
                self.currentPlay--;
            } else if (index === self.currentPlay) {
                self.playOther(index);
            }
            if (self.mList.length === 0) {
                self.animCD.pause();
                $('.toggle').css('background-position', '-34px -44px');
            }
            ev.halt();
        });

        listEl.delegate('click', '.m-operate .m-download', function (ev) {
            self.logDownload($(ev.currentTarget).parent().parent().index());
            ev.stopPropagation();
        });
    }

    Player.prototype.playOther = function (index) {
        var self = this,
            handler = $('.handler'),
            handPos = 0;

        if (this.mList.length === 0) {
            this.animImg.css('background-image', 'url(resource/img/default.jpg)');
            handler.css('left', handPos - 1 + '%');
            this.audio.remove();
            this.audio = $('<audio></audio>')[0];
            this.el.append(this.audio);
            return;
        }

        if (index >= this.mList.length)
            index = 0;
        if (index < 0)
            index = this.mList.length - 1;
        $('.m-list .list-item:nth-child(' + (this.currentPlay + 1) + ')').removeClass('playing');
        this.currentPlay = index;
        $('.m-list .list-item:nth-child(' + (this.currentPlay + 1) + ')').addClass('playing');
        this.audio.remove();
        this.audio = $('<audio src="' + this.mList[this.currentPlay].src + '"></audio>')[0];
        this.el.append(this.audio);
        $(this.audio).on('timeupdate', this.handleTimeUpdate(self));
        $(this.audio).on('progress', function () {});
        this.audio.play();
        if ($('.volIcon').hasClass('mute')) this.audio.volume = 0;

        $($('.p-info a')[0]).text(this.mList[this.currentPlay].title);
        $($('.p-info a')[1]).text(this.mList[this.currentPlay].artist);
        $($('.p-info a')[2]).text(this.mList[this.currentPlay].album);

        if (this.animCD.isPaused())
            this.animCD.resume();
        if (!this.animCD.isRunning())
            this.animCD.run();

        this.animImg.css('background-image', 'url("' + this.mList[this.currentPlay].img + '")');
        this.makeLrc();

        var idData = this.mList[this.currentPlay].id;
        new IO({
            type: "post",
            //            url: 'lyric-datas.js',
            url: 'sendListeningHistory',
            data: {
                id: idData,
                type: 'listen'
            },
            error: function (m, io) {
                console.log(m);
            }
        });
    }

    Player.prototype.mkTypeList = function (listType) {
        var container = $('.t-' + listType + ' .t-list'),
            self = this,
            tempList = [],
            d = this.musicData[listType],
            listStr = '';

        for (var i = 0; i < d.length; i++) {
            tempList.push(new XTemplate(TypeTpl).render(d[i]));
        };
        listStr = tempList.join('\n');
        container.append(listStr);

        if (this.typeListenTimes >= 4)
            return;
        container.delegate('click', '.t-list .add-plist', function (ev) {
            var t = $(ev.currentTarget);
            self.addToPlaylist(listType, t.parent().parent().index());
            ev.halt();
        });
        container.delegate('click', '.t-list .play-now', function (ev) {
            var t = $(ev.currentTarget);
            self.addToPlaylist(listType, t.parent().parent().index());
            self.playOther(self.mList.length - 1);
            $('.toggle').css('background-position', '-264px -3px');
            ev.halt();
        });
        container.delegate('click', '.t-list .download', function (ev) {
            self.logDownload($(ev.currentTarget).parent().parent().index(), listType);
        });
        container.delegate('click', '.t-singer', function (ev) {
            $('.search-result').css('display', 'none');
            $('.text-body').css('display', 'block');
            $('.detail-info').css('transform', 'rotateX(0)');
            ev.halt();
        });
        this.typeListenTimes++;
    }

    Player.prototype.logDownload = function (index, type) {
        var id;
        if (arguments.length === 2) {
            id = this.musicData[type][index].id;
        } else if (arguments.length === 1) {
            id = this.mList[index].id;
        }
        new IO({
            type: "post",
            //            url: 'lyric-datas.js',
            url: 'updateListeningHistory',
            data: {
                id: id,
                type: 'download'
            },
            error: function (m, io) {
                console.log(m);
            }
        });
    }

    Player.prototype.initTeyeWrap = function (data) {
        var self = this,
            nav = $('.type-nav'),
            navItem = nav.all('a'),
            typeSection = $('.t-section'),
            typePage = $('.t-section>li'),
            current = 1,
            isAnimating = false,
            aCount = 0;
        this.mkTypeList('chinese');
        this.mkTypeList('western');
        this.mkTypeList('jk');
        this.mkTypeList('rank');

        $(typePage[current - 1]).css({
            'opacity': 1,
            'z-index': 150
        });

        i = 1;
        navItem.each(function () {
            this[0].index = i++;
        });

        $(navItem[current - 1]).addClass('currentTab');

        nav.on('click', function (ev) {
            var index = ev.target.parentElement.index || ev.target.index;
            if (isAnimating || current === index)
                return;
            var navOutPre = {
                    transform: 'translateX(100%) scale(0.9)',
                    opacity: 0,
                    'z-index': 100
                },
                navInNext = {
                    transform: 'translateX(0)',
                    'z-index': 150,
                    opacity: 1
                },
                animOutCfg = {
                    duration: 1,
                    easing: 'cubic-bezier(0.7, 0, 0.3, 1)',
                    complete: function () {
                        --aCount;
                        if (aCount === 0) {
                            isAnimating = false;
                            current = index;
                        }
                    },
                    useTransiton: true
                },
                animInCfg = {
                    duration: 1,
                    easing: 'cubic-bezier(0.7, 0, 0.3, 1)',
                    complete: function () {
                        current = index;
                        --aCount;
                        if (aCount === 0) {
                            isAnimating = false;
                            current = index;
                        }
                    },
                    useTransiton: true
                };

            if (index && !isAnimating) {
                $(navItem[current - 1]).removeClass('currentTab');
                $(navItem[index - 1]).addClass('currentTab');

                $(typePage[current - 1]).animate(navOutPre, animOutCfg);
                ++aCount;

                $(typePage[index - 1]).css({
                    transform: 'translateX(-100%)'
                }).animate(navInNext, animInCfg);
                ++aCount;

                isAnimating = true;
            }
        });

        $('.t-chinese .more').on('click', this.handlerMore('chinese'));
        $('.t-western .more').on('click', this.handlerMore('western'));
        $('.t-jk .more').on('click', this.handlerMore('jk'));
        $('.t-rank .more').on('click', this.handlerMore('rank'));
        $('.t-section').delegate('click', '.prePage', function (ev) {
            var parentEl = $(ev.currentTarget).parent();
            var parentType = parentEl.hasClass('t-chinese') ? 'chinese' :
                parentEl.hasClass('t-western') ? 'western' :
                parentEl.hasClass('t-jk') ? 'jk' :
                parentEl.hasClass('t-rank') ? 'rank' : undefined;
            if (parentType === undefined)
                return;
            if (--self.pageIndex[parentType] === 0) {
                self.pageIndex[parentType] = 1;
                return;
            }
            getOtherPage(parentType, self.pageIndex[parentType]);
        });
        $('.t-section').delegate('click', '.nextPage', function (ev) {

            var parentEl = $(ev.currentTarget).parent();
            var parentType = parentEl.hasClass('t-chinese') ? 'chinese' :
                parentEl.hasClass('t-western') ? 'western' :
                parentEl.hasClass('t-jk') ? 'jk' :
                parentEl.hasClass('t-rank') ? 'rank' : undefined;

            if (parentType === undefined)
                return;
            var nextPage = $('.t-' + parentType + ' .nextPage');
            if (nextPage.hasClass('disable')) {
                return;
            }
            getOtherPage(parentType, ++self.pageIndex[parentType]);
        });

        function getOtherPage(listType, index) {
            new IO({
                type: "get",
                //                url: 'get-more.js',
                url: 'displayMoreSongs',
                data: {
                    type: listType,
                    index: index
                },
                success: function (data) {
                    var prePage = $('.t-' + listType + ' .prePage'),
                        nextPage = $('.t-' + listType + ' .nextPage');
                    $('.t-' + listType + ' .t-list li').remove();
                    self.musicData[listType] = data;
                    self.mkTypeList(listType);
                    if ($('.t-' + listType + ' .t-list').hasClass('fade-in')) {
                        $('.t-' + listType + ' .t-list').removeClass('fade-in');
                        $('.t-' + listType + ' .t-list').addClass('fade-in2');
                    } else {
                        $('.t-' + listType + ' .t-list').removeClass('fade-in2');
                        $('.t-' + listType + ' .t-list').addClass('fade-in');
                    }
                    prePage.css('display', 'block');
                    if (index === 1)
                        prePage.addClass('disable');
                    else if (index > 1)
                        prePage.removeClass('disable');
                    if (self.musicData[listType].length < 15) {
                        nextPage.css('display', 'block').addClass('disable');
                    } else {
                        nextPage.css('display', 'block').removeClass('disable');
                    }

                },
                error: function (m, io) {
                    console.log(m);
                },
                dataType: "json"
            });
        }
    }

    Player.prototype.handlerMore = function (listType) {
        var self = this;
        this.pageIndex[listType] = 1;

        return function () {
            new IO({
                type: "get",
                //                url: 'get-more.js',
                url: 'displayMoreSongs',
                data: {
                    type: listType,
                    index: "1"
                },
                success: function (data) {
                    $('.t-' + listType + ' .t-list li').remove();
                    self.musicData[listType] = data;
                    self.mkTypeList(listType);
                    $('.t-' + listType + ' .prePage').css('display', 'block').addClass('disable');
                    if (self.musicData[listType].length < 15) {
                        $('.t-' + listType + ' .nextPage').css('display', 'block').addClass('disable');
                    } else {
                        $('.t-' + listType + ' .nextPage').css('display', 'block');
                    }
                    $('.t-' + listType + ' .t-list').addClass('fade-in');
                },
                error: function (m, io) {
                    console.log(m);
                },
                dataType: "json"
            });
        }
    };

    Player.prototype.makeLrc = function () {
        var self = this;
        var idData = this.mList[this.currentPlay].id;
        new IO({
            type: "get",
            //            url: 'lyric-datas.js',
            url: 'getLyric',
            data: {
                id: idData
            },
            dataType: "json",
            success: function (data) {
                if (data.length === 0) {
                    $('.lyric').css('display', 'none');
                    $('.default-info').css('display', 'block');
                }
                var elStr = '';
                self.lrc = data;
                for (var i = 0; i < self.lrc.length; i++) {
                    elStr += '<p class="line" data-index = "' + i + '">' + self.lrc[i].text + '</p>';
                }
                $('.lyric .lrc-text p').remove();
                $('.lyric .lrc-text').append(elStr);
                $('.lyric').css('display', 'block');
                $('.default-info').css('display', 'none');
            },
            error: function (m, io) {
                $('.lyric').css('display', 'none');
                $('.default-info').css('display', 'block');
            }
        });

    }

    Player.prototype.addToPlaylist = function (type, index) {
        var data = this.musicData[type][index];
        this.mList.push(data);
        localStorage.setItem('playlist', JSON.stringify(this.mList));
        $('.music-list .m-list').append(new XTemplate(ListTpl).render(data));

    }

    Player.prototype.searchHandler = function () {
        var searchBar = $('.search-bar'),
            searchInput = $('.search-bar>input'),
            searchBtn = $('.search-bar>button'),
            seatchResult = [],
            hint = $('.search-hint'),
            textBody = $('.text-body'),
            self = this;

        searchInput.on('focusin', function () {
            searchBar.css('box-shadow', '0 0 6px');
        });
        searchInput.on('focusout', function () {
            searchBar.css('box-shadow', 'none');
            hint.css('visibility', 'hidden');
        });
        searchInput.on('valuechange', function (ev) {
            var key = ev.newVal;
            $('.search-hint ul li').remove();
            if (key === '')
                return;
            new IO({
                type: "get",
                //                url: "fuzzy.js",
                url: "fuzzySearch",
                data: {
                    "searchKey": key
                },
                success: function (data) {
                    var mulStr = '',
                        resultCount = 0;
                    if (data["歌曲"].length === 0 && data["歌手"].length === 0 && data["专辑"].length === 0) return;
                    for (var i = 0; i < data["歌曲"].length; i++) {
                        if (resultCount >= 5)
                            break;
                        mulStr += '<li class = "hint-item"><span class = "s-info">' + data["歌曲"][i].songName + '</span><span class = "s-type">歌曲</span></li>';
                        resultCount++;
                    }
                    for (var i = 0; i < data["歌手"].length; i++) {
                        if (resultCount >= 5)
                            break;
                        mulStr += '<li class = "hint-item"><span class = "s-info">' + data["歌手"][i].singerName + '</span><span class = "s-type">歌手</span></li>';
                        resultCount++;
                    }
                    for (var i = 0; i < data["专辑"].length; i++) {
                        if (resultCount >= 5)
                            break;
                        mulStr += '<li class = "hint-item"><span class = "s-info">' + data["专辑"][i].albumName + '</span><span class = "s-type">专辑</span></li>';
                        resultCount++;
                    }
                    $('.search-hint ul').append(mulStr);
                    $('.search-hint').css('visibility', 'visible');
                },
                error: function (m, io) {
                    console.log(m);
                },
                dataType: "json"
            });
        });
        /*console.log($('.search-hint'));
        $('.wrapper').delegate('click', '.hint-item', function (ev) {
            console.log('click hint');
            $('.search input').val($(ev.currentTarget).one('.s-info').text());
            searchKeyWord();
            ev.halt();
        });*/

        function searchKeyWord() {
            var key = searchInput.val();
            if (key === '')
                return;
            new IO({
                type: "get",
                //                url: "get-more.js",
                url: "accurateSearch",
                data: {
                    "searchKey": key
                },
                success: function (data) {
                    var dom = '';
                    for (var i = 0, len = data.length; i < len; i++) {
                        dom += (new XTemplate(TypeTpl)).render(data[i]);
                    }
                    $('.search-result>ul li').remove();
                    $('.search-result>ul').append(dom);
                    searchResult = data;
                    textBody.css('display', 'none');
                    $('.search-result').css('display', 'block');
                    $('.detail-info').css({
                        'transform': 'rotateX(0)',
                        'z-index': 200
                    });
                    $('.p-panel').css('z-index', 0);
                },
                error: function (m, io) {
                    console.log(m);
                },
                dataType: "json"
            });
        }

        $('.search-result>ul').delegate('click', '.search-result .add-plist', function (ev) {
            var t = $(ev.currentTarget);

            self.mList.push(searchResult[t.parent().parent().index()]);
            localStorage.setItem('playlist', JSON.stringify(self.mList));
            $('.music-list .m-list').append(new XTemplate(ListTpl).render(searchResult[t.parent().parent().index()]));

            ev.halt();
        });
        $('.search-result>ul').delegate('click', '.search-result .play-now', function (ev) {
            var t = $(ev.currentTarget);
            self.mList.push(searchResult[t.parent().parent().index()]);
            localStorage.setItem('playlist', JSON.stringify(self.mList));
            $('.music-list .m-list').append(new XTemplate(ListTpl).render(searchResult[t.parent().parent().index()]));
            self.playOther(self.mList.length - 1);
            $('.toggle').css('background-position', '-264px -3px');
            ev.halt();
        });
        $('.search-result>ul').delegate('click', '.t-singer', function (ev) {
            $('.detail-info').css('transform', 'rotateX(90deg)');
            ev.halt();
        });

        searchInput.on('keydown', function (ev) {
            var hintItem = $('.search-hint ul li');
            switch (ev.keyCode) {
            case 13:
                /*enter*/
                if ($('.search-hint .selected').length !== 0)
                    $('.search input').val($(hintItem[self.hintIndex]).one('.s-info').text());
                searchKeyWord();
                ev.halt();
                break;
            case 38:
                /*arrow up*/
                ev.halt();
                if (hintItem.length === 0)
                    return;
                $(hintItem[self.hintIndex]).removeClass('selected');
                if (--self.hintIndex < 0)
                    self.hintIndex = hintItem.length - 1;
                $(hintItem[self.hintIndex]).addClass('selected');
                break;
            case 40:
                /*arrow down*/
                ev.halt();
                if (hintItem.length === 0)
                    return;
                $(hintItem[self.hintIndex]).removeClass('selected');
                if (++self.hintIndex >= hintItem.length)
                    self.hintIndex = 0;
                $(hintItem[self.hintIndex]).addClass('selected');
                break;
            }
        });
        searchBtn.on('click', searchKeyWord);
    }

    Player.prototype.handleDetail = function () {
        var mainEl = $('.detail-info');
        $('.close').on('click', function () {
            mainEl.css({
                'transform': 'rotateX(90deg)',
                'z-index': 0
            });
            $('.p-panel').css('z-index', 100);
        });
    };

    return Player;
}, {
    requires: ['node', 'anim', 'xtemplate', 'io', 'dd', 'player/tpl/mListItem-xtpl', 'player/tpl/tListItem-xtpl']
});