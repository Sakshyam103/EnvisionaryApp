//**********************************************************************************************************************
// https://brand.linkedin.com/policies#:~:text=LinkedIn%20logos&text=The%20only%20use%20of%20LinkedIn,your%20organization's%20presence%20on%20LinkedIn.
// Logo for Use by LinkedIn Members:
//      The only use of LinkedIn brand features that is permitted without a separate agreement is that if you are  a LinkedIn
//      member, you may use the “in” Logo to designate a link to your personal profile or to a Group or Company page or to
//      promote you or your organization’s presence on LinkedIn.
// LinkedIn logo: https://icons8.com/icons/set/linkedin--white
//
// https://github.com/logos
// Do these awesome things
//      Use a permitted GitHub logo to link to GitHub.
//      Use the Invertocat logo as a social button to link to your GitHub profile or project.
//      Use a permitted GitHub logo to inform others that your project integrates with GitHub.
//      Use the GitHub logo in a blog post or news article about GitHub.
//      Use the permitted GitHub logos less prominently than your own company or product name or logo.//
// Please don’t do these things
//      Do not use the GitHub name or any GitHub logo in a way that suggests you are GitHub, your offering or project is by GitHub, or that GitHub is endorsing you or your offering or project.
//      Do not use any GitHub logo as the icon or logo for your business/organization, offering, project, domain name, social media account, or website.
//      Do not modify the permitted GitHub logos, including changing the color, dimensions, or combining with other words or design elements.
//      Do not use GitHub trademarks, logos, or artwork without GitHub’s prior written permission.
// GitHub inverted logo: https://icons8.com/icons/set/github--white
//**********************************************************************************************************************

import React from 'react';
import NavigationBar from "./NavigationBar.jsx";

const AboutUs = () => {
    const linkedInLogo = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAACXBIWXMAAAsTAAALEwEAmpwYAAABS0lEQVR4nO2ZO0oEQRRFnyhmOoKmgoHuwD0oOI2GomBm6BZMZMCFuAYzx1EXYqb4YUB7IpMjJR01U3a1HXhreAcqu13F4dXr6o+Z4zh/AtgHRsCE/2cC3AJFW4lLdBm0qYQ6/RSRsJ3UGaaIlOjzmSKSBeYiM1aRZ+AQWK3GMfBKhiI7Krdr6yiyPCXfI0ORYkq+T4Yib8AJsFaN0CMvZCgig7nILFXEEq5JyDxUZ9E6sAgsAdvAOfCeg0g4OHcb1l4BrtVFNhsXt5/cAnAvK9IGYAP4khcB5hMyV7IiwBnwVEUfgdNfskeSIsBBm/dvYEtV5C4y7SiS76mKjCPTjiP5OVWRTvPWcZGAV8R8a0XxHgl4j5j3SBTvETWsyyNKbiIl+nykiIS/p+rcpIgU6LPXKFLJDNDlIkmi9pV9KNIzZdhOyZVwHMfqfANKiIQn/4RZwwAAAABJRU5ErkJggg==";
    const githubLogo = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAACXBIWXMAAAsTAAALEwEAmpwYAAADv0lEQVR4nO2ZW4iNURTHP4YMcs+4X3In8eASxgMpcosXhPJCcifhlTzILZdGKY/y4M0tdx5JeDAyDDMkxqUwjfu4HD8t1qnjtM/37X3Ot8+ZNP/adTrft9Ze/73XXpf9BUEj/lMAHYC5wB7gLFAJ1ALfdMjvB/pM3pkDtA8aAoAWwGLgIvATd4jMeWCR6AoKQKAlsBF4QXyoATYAxfkiMR2oxh+qgGm+3egg+cNR2fm4SXQBbpN/3ARK4iLRV7e7UHgkNuRKorOG0UKjGuiaLYniArlTmJu5h2jgMA0PZdmE2DBsUbcbDWwFnuZgnGT7zcBIrQ72Rbw/1SXZReWJngY3FEJfgSvAemAiMBBorWOQ/rcKOAV8BNYCRWm6Rloc/mgX04wdBWP2BZpbrVbIu0A3i/nX2yQ9m7Ij3kT1rw1dLeZ/HrorWgDaYIhHIqWWNiwMUyJVrA3meySy2tKGc5kUdHAoxbd7JHLE0oYfQFuTAmmKbPBeDqRHIkOB75a2zDYp2GspvM8XiSSA45a27DYJSwtqg7GBZwDzLG05bRKWRGPjl83yQGSwJZFKk/BbC8G3vkkIgF6WRN4E6dCbjijIO00CzwCGp3lBrY5fafbUm4STkWKS1jv9gI5Ad/2dHN6JpEJcWVODjCZaC/ZILqxJoE4f3gDuAI/V3V7p7+RYEHgGUBbiFZ9TjkGtSfihPryqpfQ6idNasY7QlvfPquSBSFOdqzcwTCIlMFMr5516FyZ4YBK+oA93aBm+HzgBXAPKdTfe6TlZ4nk36vVMPAEqgFvAGeCQ9kJ7M5YpUnZgj0+yUx5ILDcc6DBsMymZghu+AEvjcDVtH3Y5khBMNilrZRmC03Fdz1JRFgRa62KI27qiPmNflHJO0iF9yjJJQCGKXwPHgJWyUkB/oF2K7k7Sx8h1KLBJygttd7PF2bAVkpXNFPJKtQcXg23cToztkqK7RP+TZ3FgRlTYk0hhQrk+HwN8iCBRGjJHaQxkqsSWKL+V0JsJM1NKiAvqGsmPOM+Ay8Cs0An+yq/Ikchqq9sN4K6zXzpAdzbTHFG4Y12BAxOAhEFJIq78oUHBFQlgnOtEkkVNqNOLtZ6Rfhquf0AWRA5mM1FzrbtcUOSo3wWXXC4A0ydr6+LLWei3RUXOX4CBPsD9AhK5Jx1jTiRSJmwjlWYBiFyO/Vu8+nRZhmgWN5EEcMDrRQcwSvsDX0TKgfF+rDf30WvTvqPUZKHnZYq86FqTj+umTBlaWtCTyfLFUX62ys7IJSc1ohGBP/wGjidhuRxqAwcAAAAASUVORK5CYII=";

    return (
        <div>
            <NavigationBar style={{ textAlign: 'center'}}>
                <h2 style={{ color: 'antiquewhite' }}>About us</h2>
                <h2 style={{ color: 'antiquewhite' }}>The software developers of the Envisionary App:</h2>
            </NavigationBar>
            <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'center', marginTop: '5%' }}>
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginRight: '5%' }}>
                    <h3 style={{ color: 'antiquewhite' }}>Kritika Parajuli</h3>
                    <p>
                        <img src={linkedInLogo} alt="LinkedIn Logo" style={{ marginRight: '8px' }} />
                        <a href="https://www.linkedin.com/in/kritika-parajuli-517b46248/" target="_blank" rel="noopener noreferrer" style={{ color: 'pink' }}>
                            Kritika Parajuli
                        </a>
                    </p>
                    <p>
                        <img src={githubLogo} alt="GitHub Logo" style={{ marginRight: '8px' }} />
                        <a href="https://github.com/kparajul" target="_blank" rel="noopener noreferrer" style={{ color: 'pink' }}>
                            kparajul
                        </a>
                    </p>
                </div>
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                    <h3 style={{ color: 'antiquewhite' }}>Sakshyam Sarki</h3>
                    <p>
                        <img src={linkedInLogo} alt="LinkedIn Logo" style={{ marginRight: '8px' }} />
                        <a href="https://www.linkedin.com/in/sakshyam-sarki-baa47123b/" target="_blank" rel="noopener noreferrer" style={{ color: 'pink' }}>
                            Sakshyam Sarki
                        </a>
                    </p>
                    <p>
                        <img src={githubLogo} alt="GitHub Logo" style={{ marginRight: '8px' }} />
                        <a href="https://github.com/Sakshyam103" target="_blank" rel="noopener noreferrer" style={{ color: 'pink' }}>
                            Sakshyam103
                        </a>
                    </p>
                </div>
            </div>
            <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'center', marginTop: '5%' }}>
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginRight: '5%' }}>
                    <h3 style={{ color: 'antiquewhite' }}>Brandon LaPointe</h3>
                    <p>
                        <img src={linkedInLogo} alt="LinkedIn Logo" style={{ marginRight: '8px' }} />
                        <a href="https://www.linkedin.com/in/brandon-lapointe-414427273/" target="_blank" rel="noopener noreferrer" style={{ color: 'pink' }}>
                            Brandon LaPointe
                        </a>
                    </p>
                    <p>
                        <img src={githubLogo} alt="GitHub Logo" style={{ marginRight: '8px' }} />
                        <a href="https://github.com/Brandomon" target="_blank" rel="noopener noreferrer" style={{ color: 'pink' }}>
                            Brandomon
                        </a>
                    </p>
                </div>
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                    <h3 style={{ color: 'antiquewhite' }}>Molly Morano</h3>
                    <p>
                        <img src={linkedInLogo} alt="LinkedIn Logo" style={{ marginRight: '8px' }} />
                        <a href="https://www.linkedin.com/in/molly-morano/" target="_blank" rel="noopener noreferrer" style={{ color: 'pink' }}>
                            Molly Morano
                        </a>
                    </p>
                    <p>
                        <img src={githubLogo} alt="GitHub Logo" style={{ marginRight: '8px' }} />
                        <a href="https://github.com/mmorano401" target="_blank" rel="noopener noreferrer" style={{ color: 'pink' }}>
                            mmorano401
                        </a>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default AboutUs;