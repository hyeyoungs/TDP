"use strict";

//Rage Template
//Designerd by: http://bootstrapthemes.co

jQuery(document).ready(function ($) {

//for Preloader

    $(window).load(function () {
        $("#loading").fadeOut(500);
    });


    /*---------------------------------------------*
     * Mobile menu
     ---------------------------------------------*/
    $('#navbar-menu').find('a[href*="#"]:not([href="#"])').click(function () {
        if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
            var target = $(this.hash);
            target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
            if (target.length) {
                $('html,body').animate({
                    scrollTop: (target.offset().top - 80)
                }, 1000);
                if ($('.navbar-toggle').css('display') != 'none') {
                    $(this).parents('.container').find(".navbar-toggle").trigger("click");
                }
                return false;
            }
        }
    });



    /*---------------------------------------------*
     * WOW
     ---------------------------------------------*/

    var wow = new WOW({
        mobile: false // trigger animations on mobile devices (default is true)
    });
    wow.init();

// magnificPopup

    $('.popup-img').magnificPopup({
        type: 'image',
        gallery: {
            enabled: true
        }
    });


//
// // slick slider active Home Page Tow
//     $(".testimonial_slid").slick({
//         dots: false,
//         infinite: false,
//         slidesToShow: 1,
//         slidesToScroll: 1,
//         arrows: true,
//         prevArrow: "<i class='fa fa-angle-left nextprevleft'></i>",
//         nextArrow: "<i class='fa fa-angle-right nextprevright'></i>",
//         autoplay: true,
//         autoplaySpeed: 2000
//     });
//
//
//
// //    featured slider
//     $('.featured_slider').slick({
//         centerMode: true,
//         dote: true,
//         centerPadding: '60px',
//         slidesToShow: 3,
//         speed: 1500,
//         index: 2,
//         responsive: [
//             {
//                 breakpoint: 768,
//                 settings: {
//                     arrows: false,
//                     centerMode: true,
//                     centerPadding: '40px',
//                     slidesToShow: 1
//                 }
//             },
//             {
//                 breakpoint: 480,
//                 settings: {
//                     arrows: false,
//                     centerMode: true,
//                     centerPadding: '40px',
//                     slidesToShow: 1
//                 }
//             }
//         ]
//     });
//
//

//---------------------------------------------
// Counter 
//---------------------------------------------

    $('.statistic-counter').counterUp({
        delay: 10,
        time: 2000
    });

//---------------------------------------------
// Scroll Up 
//---------------------------------------------

    $(window).scroll(function () {
        if ($(this).scrollTop() > 600) {
            $('.scrollup').fadeIn('slow');
        } else {
            $('.scrollup').fadeOut('slow');
        }
    });
    $('.scrollup').click(function () {
        $("html, body").animate({scrollTop: 0}, 1000);
        return false;
    });





//About us accordion 

    $("#faq_main_content").collapse({
        accordion: true,
        open: function () {
            this.addClass("open");
            this.css({height: this.children().outerHeight()});
        },
        close: function () {
            this.css({height: "0px"});
            this.removeClass("open");
        }
    });



//
//
// //Team Skillbar active js
//
//     jQuery('.teamskillbar').each(function () {
//         jQuery(this).find('.teamskillbar-bar').animate({
//             width: jQuery(this).attr('data-percent')
//         }, 6000);
//     });



//Span Menu toggle
	$(".navbar-header button").on('click', function(){
		$('.navbar-header button').toggleClass('active');
	});

  

    //End

});














